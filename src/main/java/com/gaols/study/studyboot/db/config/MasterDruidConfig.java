package com.gaols.study.studyboot.db.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.datasource.druid.master")
public class MasterDruidConfig extends DruidConfig {
}
