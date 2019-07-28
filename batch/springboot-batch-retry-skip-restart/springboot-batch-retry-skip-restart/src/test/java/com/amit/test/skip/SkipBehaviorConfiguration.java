package com.amit.test.skip;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.amit.jpa.repositories.ProductRepository;
import com.amit.test.AbstractRobustnessBatchConfiguration;
import com.amit.test.JpaH2Configuration;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@EnableBatchProcessing
@EnableJpaRepositories(basePackageClasses = { ProductRepository.class })
@Import({ JpaH2Configuration.class })
public class SkipBehaviorConfiguration extends AbstractRobustnessBatchConfiguration {

	@Autowired
	ItemReader<String> reader;

	@Autowired
	ItemProcessor<String, String> processor;

	@Autowired
	ItemWriter<String> writer;

	@Autowired
	SkipListener skipListener;

	@Bean
	public Job importProductsJob() {
		Step importProductsStep = stepBuilders.get("importProductsStep")
		                                      .<String, String>chunk(5)
		                                      .reader(reader)
		                                      .processor(processor)
		                                      .writer(writer)
		                                      .faultTolerant()
		                                      .skipLimit(5)
		                                      .skip(FlatFileParseException.class)
		                                      .skip(DataIntegrityViolationException.class)
		                                      .listener(skipListener)
		                                      .build();

		return jobBuilders.get("importProductsJob")
		                  .start(importProductsStep)
		                  .build();
	}

}
