package com.gaols.study.studyboot.db.config;

import com.jfinal.plugin.activerecord.DynamicDataSourceContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("Current DataSource is [{}]", DynamicDataSourceContextHolder.getDataSourceKey());
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }

    public void switchDataSource(DataSourceKey key) {
        switchDataSource(key.name());
    }

    public void switchDataSource(String key) {
        DynamicDataSourceContextHolder.setDataSourceKey(key);
    }
}
