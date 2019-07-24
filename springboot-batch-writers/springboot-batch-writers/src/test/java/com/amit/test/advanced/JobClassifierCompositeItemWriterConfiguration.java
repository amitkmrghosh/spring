package com.amit.test.advanced;

import java.util.HashMap;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.BackToBackPatternClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.amit.advanced.ProductRouterClassifier;
import com.amit.model.Product;
import com.amit.repository.ProductRepository;
import com.amit.step.DeleteProductItemWriter;
import com.amit.step.InsertProductItemWriter;
import com.amit.test.AbstractBatchConfiguration;
import com.amit.test.FlatFileReaderConfiguration;


@Configuration
@EnableBatchProcessing
@EnableJpaRepositories(basePackageClasses = { ProductRepository.class })
@Import({ FlatFileReaderConfiguration.class })
public class JobClassifierCompositeItemWriterConfiguration extends AbstractBatchConfiguration {

	@Autowired
	EntityManagerFactory emf;

	@Autowired
	ItemReader<Product> productItemReader;


	@Bean
	public Job writeProductsJob() {
		return jobBuilders.get("writeProductJob")
		                  .start(readWriteStep())
		                  .build();
	}

	@Bean
	public Step readWriteStep() {
		return stepBuilders.get("readWriteStep")
		                   .<Product, Product>chunk(3)
		                   .reader(productItemReader)
		                   .writer(productItemWriter())
		                   .build();
	}

	@Bean
	public ItemWriter<Product> productItemWriter() {
		BackToBackPatternClassifier classifier = new BackToBackPatternClassifier();
		classifier.setRouterDelegate(new ProductRouterClassifier());
		classifier.setMatcherMap(new HashMap<String, ItemWriter<? extends Product>>() {{
			put("C", insertJpaBatchItemWriter());
			put("U", insertJpaBatchItemWriter());
			put("D", deleteJpaBatchItemWriter());
		}});

		ClassifierCompositeItemWriter<Product> writer = new ClassifierCompositeItemWriter<Product>();
		writer.setClassifier(classifier);
		return writer;
	}

	@Bean
	public ItemWriter<Product> insertJpaBatchItemWriter() {
		return new InsertProductItemWriter();
	}

	@Bean
	public ItemWriter<Product> deleteJpaBatchItemWriter() {
		return new DeleteProductItemWriter();
	}
}
