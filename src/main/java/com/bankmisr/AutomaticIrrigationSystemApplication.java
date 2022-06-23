package com.bankmisr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutomaticIrrigationSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutomaticIrrigationSystemApplication.class, args);
	}

}
