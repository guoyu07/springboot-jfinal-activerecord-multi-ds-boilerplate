package com.gaols.study.studyboot.db.config;

import com.jfinal.plugin.activerecord.DynamicDataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(1)
@Component
public class DynamicDataSourceAspect {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    /**
     * Switch DataSource.
     */
    @Before("@annotation(targetDataSource))")
    public void switchDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        DynamicDataSourceContextHolder.setDataSourceKey(targetDataSource.value());
        logger.info("Switch DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }

    /**
     * Restore DataSource.
     */
    @After("@annotation(targetDataSource))")
    public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
        DynamicDataSourceContextHolder.clearDataSourceKey();
        logger.info("Restore DataSource to [{}] in Method [{}]", DynamicDataSourceContextHolder.getDataSourceKey(), point.getSignature());
    }

}
