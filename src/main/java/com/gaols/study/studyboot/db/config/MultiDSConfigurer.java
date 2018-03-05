package com.gaols.study.studyboot.db.config;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.sf.jfinal.qs.model.master._MasterMappingKit;
import com.sf.jfinal.qs.model.slave._SlaveMappingKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableConfigurationProperties({MasterDruidConfig.class, SlaveDruidConfig.class})
@Configuration
public class MultiDSConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(MultiDSConfigurer.class);

    private MasterDruidConfig masterDruidConfig;
    private SlaveDruidConfig slaveDruidConfig;

    @Primary
    @Bean("master")
    public DataSource master() {
        return createDruidPlugin(masterDruidConfig).getDataSource();
    }

    @Bean("slave")
    public DataSource slave() {
        return createDruidPlugin(slaveDruidConfig).getDataSource();
    }

    private DruidPlugin createDruidPlugin(DruidConfig config) {
        DruidPlugin druidPlugin = new DruidPlugin(config.getUrl(), config.getUsername(), config.getPassword());
        druidPlugin.start();
        return druidPlugin;
    }

    @Bean
    @DependsOn("master")
    public ActiveRecordPlugin activeMaster() {
        ActiveRecordPlugin plugin = new ActiveRecordPlugin("master", master(), dynamicDataSource());
        _MasterMappingKit.mapping(plugin);
        plugin.start();
        return plugin;
    }

    @Bean
    @DependsOn("slave")
    public ActiveRecordPlugin activeSlave() {
        ActiveRecordPlugin plugin = new ActiveRecordPlugin("slave", slave(), dynamicDataSource());
        _SlaveMappingKit.mapping(plugin);
        plugin.start();
        return plugin;
    }

    @Autowired
    public void setMasterDruidConfig(MasterDruidConfig masterDruidConfig) {
        this.masterDruidConfig = masterDruidConfig;
    }

    @Autowired
    public void setSlaveDruidConfig(SlaveDruidConfig slaveDruidConfig) {
        this.slaveDruidConfig = slaveDruidConfig;
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceKey.master.name(), master());
        dataSourceMap.put(DataSourceKey.slave.name(), slave());
        dynamicRoutingDataSource.setDefaultTargetDataSource(master());
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        return dynamicRoutingDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
