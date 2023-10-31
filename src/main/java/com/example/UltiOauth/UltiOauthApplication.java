package com.example.UltiOauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan(basePackages = "com.example.UltiOauth.Entity")
public class UltiOauthApplication {

	public static void main(String[] args) {
		SpringApplication.run(UltiOauthApplication.class, args);
	}

}
