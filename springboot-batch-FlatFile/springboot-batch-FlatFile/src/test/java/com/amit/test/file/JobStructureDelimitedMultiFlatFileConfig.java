package com.amit.test.file;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;

import com.amit.model.BookProduct;
import com.amit.model.MobilePhoneProduct;
import com.amit.model.Product;
import com.amit.test.AbstractBatchConfiguration;
import com.amit.test.DummyProductItemWriter;

@Configuration
@EnableBatchProcessing
public class JobStructureDelimitedMultiFlatFileConfig extends AbstractBatchConfiguration {

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
	public ItemReader<Product> productItemReader() throws Exception {
		FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();
		reader.setResource(new ClassPathResource("/test/input/multi-products-delimited.txt"));
		reader.setLinesToSkip(1);
		reader.setLineMapper(productLineMapper());

		return reader;
	}

	@Bean
	public ItemWriter<Product> productItemWriter() {
		return new DummyProductItemWriter();
	}

	@Bean
	public LineMapper<Product> productLineMapper() throws Exception {
		// HINT: 한 파일에 여러 종류의 데이터가 혼재해 있을 때 씁니다.

		PatternMatchingCompositeLineMapper<Product> mapper = new PatternMatchingCompositeLineMapper<>();

		Map<String, LineTokenizer> tokenizers = new HashMap<String, LineTokenizer>();
		tokenizers.put("PRM*", mobilePhoneProductLineTokenizer());
		tokenizers.put("PRB*", bookProductLineTokenizer());
		mapper.setTokenizers(tokenizers);

		Map<String, FieldSetMapper<Product>> mappers = new HashMap<String, FieldSetMapper<Product>>();
		mappers.put("PRM*", mobilePhoneProductFieldSetMapper());
		mappers.put("PRB*", bookProductFieldSetMapper());
		mapper.setFieldSetMappers(mappers);

		return mapper;
	}


	@Bean
	public LineTokenizer mobilePhoneProductLineTokenizer() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setNames(new String[] { "id", "name", "description", "manufacturer", "price" });
		return tokenizer;
	}

	@Bean
	public LineTokenizer bookProductLineTokenizer() {
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer(",");
		tokenizer.setNames(new String[] { "id", "name", "description", "publisher", "price" });
		return tokenizer;
	}

	@Bean
	public FieldSetMapper<Product> mobilePhoneProductFieldSetMapper() throws Exception {
		BeanWrapperFieldSetMapper<Product> mapper =
				new BeanWrapperFieldSetMapper<Product>();

		mapper.setPrototypeBeanName("mobilePhoneProduct");
		mapper.afterPropertiesSet();
		return mapper;
	}

	@Bean
	@Scope("prototype")
	public MobilePhoneProduct mobilePhoneProduct() {
		return new MobilePhoneProduct();
	}

	@Bean
	public FieldSetMapper<Product> bookProductFieldSetMapper() throws Exception {
		BeanWrapperFieldSetMapper<Product> mapper =
				new BeanWrapperFieldSetMapper<Product>();

		mapper.setPrototypeBeanName("bookProduct");
		mapper.afterPropertiesSet();
		return mapper;
	}

	@Bean
	@Scope("prototype")
	public BookProduct bookProduct() {
		return new BookProduct();
	}
}

