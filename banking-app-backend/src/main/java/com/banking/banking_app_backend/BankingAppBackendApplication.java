package com.banking.banking_app_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BankingAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingAppBackendApplication.class, args);
	}

}
