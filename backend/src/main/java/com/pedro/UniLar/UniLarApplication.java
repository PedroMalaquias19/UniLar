package com.pedro.UniLar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UniLarApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniLarApplication.class, args);
	}

}
