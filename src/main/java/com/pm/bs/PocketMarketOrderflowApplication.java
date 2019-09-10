package com.pm.bs;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "com.pm.common.entities")
@EnableTransactionManagement
public class PocketMarketOrderflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(PocketMarketOrderflowApplication.class, args);
	}

	@Bean("producttypes")
	public Map<String, String> getProductTypes() {
		Map<String, String> prods = new LinkedHashMap<>();
		prods.put("11001", "Basmati");
		prods.put("11002", "Brown rice");
		prods.put("11003", "Masoor dal");
		prods.put("11004", "Moong dal");
		prods.put("11005", "Indain rice");
		prods.put("11006", "Parboiled rice");
		prods.put("11007", "Channa dal");
		prods.put("11008", "Toor dal");
		return prods;
	}

}
