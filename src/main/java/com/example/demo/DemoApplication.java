package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SuppressWarnings("all")
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.setProperty("WORKDIR","D:\\develop\\oauth");
		SpringApplication.run(DemoApplication.class, args);
	}
}
