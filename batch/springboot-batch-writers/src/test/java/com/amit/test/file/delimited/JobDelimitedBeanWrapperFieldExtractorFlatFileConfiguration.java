package com.amit.test.file.delimited;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.FileSystemResource;

import com.amit.model.Product;
import com.amit.test.AbstractBatchConfiguration;
import com.amit.test.FlatFileReaderConfiguration;


@Configuration
@EnableBatchProcessing
@Import({ FlatFileReaderConfiguration.class })
public class JobDelimitedBeanWrapperFieldExtractorFlatFileConfiguration extends AbstractBatchConfiguration {

	public static final String OUTPUT_FILE = "target/outputs/delimited-BreanWrapperExtractor.txt";

	@Autowired
	FlatFileItemReader<Product> productItemReader;

	@Bean
	public Job writeProductJob() {
		Step step = stepBuilders.get("readWrite")
		                        .<Product, Product>chunk(10)
		                        .reader(productItemReader)
		                        .writer(productItemWriter())
		                        .build();

		return jobBuilders.get("writeProductJob")
		                  .start(step)
		                  .build();
	}

	@Bean
	public FlatFileItemWriter<Product> productItemWriter() {
		FlatFileItemWriter<Product> writer = new FlatFileItemWriter<Product>();
		writer.setResource(new FileSystemResource(OUTPUT_FILE));
		writer.setLineAggregator(lineAggregator());

		return writer;
	}

	@Bean
	public DelimitedLineAggregator<Product> lineAggregator() {

		BeanWrapperFieldExtractor<Product> extractor = new BeanWrapperFieldExtractor<Product>();
		extractor.setNames(new String[] { "id", "price", "name" });
		DelimitedLineAggregator<Product> lineAggregator = new DelimitedLineAggregator<Product>();
		lineAggregator.setFieldExtractor(extractor);
		return lineAggregator;
	}
}
