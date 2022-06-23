package com.bankmisr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
	
	 @Value("${irrigation.failed.transactions.trials}")
	 private int irrigationFailedTransactionTrials;
	 
	 
	 @Bean
	 public int getIrrigationFailedTransactionTrialsConfig(){
		 return this.irrigationFailedTransactionTrials;
	 }

}
