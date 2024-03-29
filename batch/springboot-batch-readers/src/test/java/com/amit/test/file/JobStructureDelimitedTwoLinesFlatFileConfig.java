package com.amit.test.file;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.separator.RecordSeparatorPolicy;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.amit.file.ProductFieldSetMapper;
import com.amit.file.TwoLineProductRecordSeparatorPolicy;
import com.amit.model.Product;
import com.amit.test.AbstractBatchConfiguration;
import com.amit.test.DummyProductItemWriter;

@Configuration
@EnableBatchProcessing
public class JobStructureDelimitedTwoLinesFlatFileConfig extends AbstractBatchConfiguration {

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
		reader.setResource(new ClassPathResource("/test/input/products-delimited-two-lines.txt"));
		reader.setLinesToSkip(1);
		reader.setLineMapper(productLineMapper());
		reader.setRecordSeparatorPolicy(recordSeparatorPolicy());
		return reader;
	}

	@Bean
	public ItemWriter<Product> productItemWriter() {
		return new DummyProductItemWriter();
	}

	@Bean
	public LineMapper<Product> productLineMapper() {
		DefaultLineMapper<Product> mapper = new DefaultLineMapper<Product>();
		mapper.setLineTokenizer(productLineTokenizer());
		mapper.setFieldSetMapper(productFieldSetMapper());

		return mapper;
	}

	@Bean
	public RecordSeparatorPolicy recordSeparatorPolicy() {
		return new TwoLineProductRecordSeparatorPolicy();
	}

	@Bean
	public LineTokenizer productLineTokenizer() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setNames(new String[] { "id", "name", "description", "price" });
		return tokenizer;
	}

	@Bean
	public FieldSetMapper<Product> productFieldSetMapper() {
		return new ProductFieldSetMapper();
	}
}