package com.quickbana.quickbana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.quickbana.quickbana")
public class QuickbanaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickbanaApplication.class, args);
	}

}
