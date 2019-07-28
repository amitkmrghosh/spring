package com.javasampleapproach.springbatch.restartstep;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class SpringBatchRestartStepConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchRestartStepConfigApplication.class, args);
	}
}
