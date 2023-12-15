package com.example.UltiOauth;

import com.example.UltiOauth.Helper.RoutingDataSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EntityScan(basePackages = "com.example.UltiOauth.Entity")
public class UltiOauthApplication {

	public static void main(String[] args) {

		SpringApplication.run(UltiOauthApplication.class, args);
	}

}
