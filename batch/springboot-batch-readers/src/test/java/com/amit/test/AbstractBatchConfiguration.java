package com.amit.test;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public abstract class AbstractBatchConfiguration {

	@Autowired
	public StepBuilderFactory stepBuilders;

	@Autowired
	public JobBuilderFactory jobBuilders;

}
