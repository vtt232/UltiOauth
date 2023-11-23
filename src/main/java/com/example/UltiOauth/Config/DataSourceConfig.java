package com.example.UltiOauth.Config;

import com.example.UltiOauth.Helper.RoutingDataSource;
import com.example.UltiOauth.UltiOauthApplication;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Value("${app.masterdb}")
    private String masterdb;

    @Value("${app.slavedb}")
    private String slavedb;


    @Bean("${app.masterdb}")
    @ConfigurationProperties(prefix = "spring.datasource")
    DataSource masterDataSource() {
        log.info("create master datasource...");
        return DataSourceBuilder.create().build();
    }


    @Bean("${app.slavedb}")
    @ConfigurationProperties(prefix = "spring.ro-datasource")
    DataSource slaveDataSource() {
        log.info("create slave datasource...");
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    DataSource primaryDataSource(@Autowired @Qualifier("${app.masterdb}") DataSource masterDataSource,
                                 @Autowired @Qualifier("${app.slavedb}") DataSource slaveDataSource) {
        log.info("Creating routing datasource...");
        Map<Object, Object> map = new HashMap<>();
        map.put(masterdb, masterDataSource);
        map.put(slavedb, slaveDataSource);
        RoutingDataSource routing = new RoutingDataSource();
        routing.setTargetDataSources(map);
        routing.setDefaultTargetDataSource(masterDataSource);
        return routing;
    }
}
