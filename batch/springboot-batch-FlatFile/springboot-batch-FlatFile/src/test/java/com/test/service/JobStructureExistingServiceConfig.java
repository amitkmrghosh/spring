package com.test.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.amit.model.Product;
import com.amit.service.ProductService;
import com.amit.service.ProductServiceAdapter;
import com.amit.test.AbstractBatchConfiguration;
import com.amit.test.DummyProductItemWriter;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackageClasses = { ProductServiceAdapter.class })
public class JobStructureExistingServiceConfig extends AbstractBatchConfiguration {

	@Bean
	public Job importProductsJob(ItemReader<Product> productItemReader) throws Exception {

		Step step = stepBuilders.get("importProductsJob")
		                        .<Product, Product>chunk(100)
		                        .reader(productItemReader)
		                        .writer(productItemWriter())
		                        .build();

		return jobBuilders.get("importProductsJob")
		                  .start(step)
		                  .build();
	}

	@Bean
	public ItemReader<Product> productItemReader(ProductServiceAdapter productServiceAdapter) throws Exception {
		ItemReaderAdapter<Product> reader = new ItemReaderAdapter<Product>();
		reader.setTargetObject(productServiceAdapter);
		reader.setTargetMethod("getProduct");

		return reader;
	}

	@Bean
	public ProductServiceAdapter productServiceAdapter(ProductService productService) throws Exception {
		ProductServiceAdapter adapter = new ProductServiceAdapter();
		adapter.setProductService(productService);
		adapter.afterPropertiesSet();

		return adapter;
	}

	@Bean
	public ItemWriter<Product> productItemWriter() {
		return new DummyProductItemWriter();
	}
}
