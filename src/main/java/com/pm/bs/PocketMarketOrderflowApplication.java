package com.pm.bs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "com.pm.common.entities")
@EnableTransactionManagement
public class PocketMarketOrderflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocketMarketOrderflowApplication.class, args);
	}

}
