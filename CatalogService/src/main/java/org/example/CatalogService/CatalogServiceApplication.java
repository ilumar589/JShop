package org.example.CatalogService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogServiceApplication {

	public static void main(String[] args) {
		try(var context = SpringApplication.run(CatalogServiceApplication.class, args)) {
			context.getApplicationStartup();
		}
	}

}
