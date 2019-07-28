package com.amit.test.file;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.JsonLineMapper;
import org.springframework.batch.item.file.separator.JsonRecordSeparatorPolicy;
import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.amit.file.ProductFieldSetMapper;
import com.amit.file.WrappedJsonLineMapper;
import com.amit.model.Product;
import com.amit.test.AbstractBatchConfiguration;
import com.amit.test.DummyProductItemWriter;


@Configuration
@EnableBatchProcessing
public class JobStructureJsonFlatFileConfig extends AbstractBatchConfiguration {

	@Bean
	public Job importProductsJob() throws Exception {

		Step step = stepBuilders.get("importProductsJob")
		                        .<Product, Product>chunk(100)
		                        .reader(productItemReader())
		                        .writer(productItemWriter())
		                        .build();

		return jobBuilders.get("importProductsJob")
		                  .start(step)
		                  .build();
	}

	@Bean
	public ItemReader<Product> productItemReader() {
		FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();
		reader.setResource(new ClassPathResource("/test/input/products.json"));
		reader.setRecordSeparatorPolicy(productRecordSeparatorPolicy());
		reader.setLineMapper(productLineMapper());

		return reader;
	}

	@Bean
	public RecordSeparatorPolicy productRecordSeparatorPolicy() {
		return new JsonRecordSeparatorPolicy();
	}

	@Bean
	public ItemWriter<Product> productItemWriter() {
		return new DummyProductItemWriter();
	}

	@Bean
	public LineMapper<Product> productLineMapper() {

		WrappedJsonLineMapper lineMapper = new WrappedJsonLineMapper();
		lineMapper.setDelegate(targetProductsLineMapper());

		return lineMapper;
	}

	@Bean
	public JsonLineMapper targetProductsLineMapper() {
		return new JsonLineMapper();
	}

	@Bean
	public FieldSetMapper<Product> productFieldSetMapper() {
		return new ProductFieldSetMapper();
	}
}

