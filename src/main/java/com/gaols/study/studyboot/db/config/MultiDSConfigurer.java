package com.gaols.study.studyboot.db.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.sf.jfinal.qs.model.master._MasterMappingKit;
import com.sf.jfinal.qs.model.slave._SlaveMappingKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiDSConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(MultiDSConfigurer.class);

    @Bean("master")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource master() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource slave() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public ActiveRecordPlugin activeMaster() {
        ActiveRecordPlugin plugin = new ActiveRecordPlugin("master", master(), dynamicDataSource());
        _MasterMappingKit.mapping(plugin);
        plugin.start();
        return plugin;
    }

    @Bean
    public ActiveRecordPlugin activeSlave() {
        ActiveRecordPlugin plugin = new ActiveRecordPlugin("slave", slave(), dynamicDataSource());
        _SlaveMappingKit.mapping(plugin);
        plugin.start();
        return plugin;
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceKey.master.name(), new SqlAwareDataSource(master(), true));
        dataSourceMap.put(DataSourceKey.slave.name(), new SqlAwareDataSource(slave(), true));
        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceMap.get(DataSourceKey.master.name()));
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        return dynamicRoutingDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
