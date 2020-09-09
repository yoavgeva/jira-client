package com.datorama.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.datorama" })
public class DatoramaMicroApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatoramaMicroApplication.class, args);
	}
}
