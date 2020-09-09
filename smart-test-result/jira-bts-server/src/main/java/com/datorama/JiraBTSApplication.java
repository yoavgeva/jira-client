package com.datorama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.datorama")
public class JiraBTSApplication {
	public static void main(String[] args) {
		SpringApplication.run(JiraBTSApplication.class, args);
	}
}
