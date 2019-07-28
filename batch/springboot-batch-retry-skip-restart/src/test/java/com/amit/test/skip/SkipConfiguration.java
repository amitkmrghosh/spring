package com.amit.test.skip;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.ResourceUtils;

import com.amit.Product;
import com.amit.ProductFieldSetMapper;
import com.amit.ProductJpaItemWriter;
import com.amit.jpa.repositories.ProductRepository;
import com.amit.skip.DatabaseSkipListener;
import com.amit.skip.ExceptionSkipPolicy;
import com.amit.skip.Slf4jSkipListener;
import com.amit.test.AbstractRobustnessBatchConfiguration;
import com.amit.test.JpaH2Configuration;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@EnableBatchProcessing
@EnableJpaRepositories(basePackageClasses = { ProductRepository.class })
@Import({ JpaH2Configuration.class })
public class SkipConfiguration extends AbstractRobustnessBatchConfiguration {
	Logger log = LoggerFactory.getLogger(SkipConfiguration.class);
	
	@Autowired
	ItemReader<Product> productReader;

	@Bean
	public Job importProductsJob() {
		Step importProductsStep = stepBuilders.get("importProductsStep")
		                                      .<Product, Product>chunk(3)
		                                      .reader(productReader)
		                                      .writer(productItemWriter())
		                                      .faultTolerant().skipLimit(2).skip(FlatFileParseException.class)
		                                      .listener(slf4jSkipListener())
		                                      .listener(databaseSkipListener())
		                                      .build();

		return jobBuilders.get("importProductsJob")
		                  .start(importProductsStep)
		                  .build();
	}

	@Bean
	public Job importProductsJobWithSkipPolicy() {
		Step importProductsStepWithSkipPolicy = stepBuilders.get("importProductsStepWithSkipPolicy")
		                                                    .<Product, Product>chunk(3)
		                                                    .faultTolerant().skipPolicy(skipPolicy())
		                                                    .reader(productReader)
		                                                    .writer(productItemWriter())
		                                                    .build();

		return jobBuilders.get("importProductsJobWithSkipPolicy")
		                  .start(importProductsStepWithSkipPolicy)
		                  .build();
	}


	@Bean
	public ExceptionSkipPolicy skipPolicy() {
		return new ExceptionSkipPolicy(FlatFileParseException.class);
	}

	@Bean
	public Slf4jSkipListener slf4jSkipListener() {
		return new Slf4jSkipListener();
	}

	@Bean
	public DatabaseSkipListener databaseSkipListener() {
		return new DatabaseSkipListener();
	}

	@Bean
	@StepScope
	public FlatFileItemReader<Product> productReader(@Value("#{jobParameters['inputFile']}") String inputFile) {

		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setNames(new String[] { "PRODUCT_ID", "NAME", "DESCRIPTION", "PRICE" });

		DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<Product>();
		lineMapper.setLineTokenizer(tokenizer);
		lineMapper.setFieldSetMapper(new ProductFieldSetMapper());

		FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();

		try {
			reader.setResource(new FileSystemResource(ResourceUtils.getFile(inputFile)));
		} catch (FileNotFoundException e) {
			log.error("파일을 찾을 수 없습니다. inputFile=" + inputFile, e);
			throw new RuntimeException(e);
		}
		reader.setLinesToSkip(1);
		reader.setLineMapper(lineMapper);

		return reader;
	}

	@Bean
	public ProductJpaItemWriter productItemWriter() {
		return new ProductJpaItemWriter();
	}
}
