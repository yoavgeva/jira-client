package com.datorama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.datorama")
@EnableDiscoveryClient
public class SmartTestResultApplication {
	public static void main(String[] args) {
		SpringApplication.run(SmartTestResultApplication.class, args);
	}
}
