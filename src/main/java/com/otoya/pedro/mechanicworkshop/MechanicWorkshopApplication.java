package com.otoya.pedro.mechanicworkshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@ComponentScan(basePackages = "com.otoya.pedro.mechanicworkshop")
public class MechanicWorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MechanicWorkshopApplication.class, args);
	}


}
