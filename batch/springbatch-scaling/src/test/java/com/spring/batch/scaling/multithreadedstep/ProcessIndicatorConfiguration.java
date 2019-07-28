package com.spring.batch.scaling.multithreadedstep;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.spring.batch.scaling.AbstractBatchConfiguration;
import com.spring.batch.scaling.JpaH2Configuration;
import com.spring.batch.scaling.domain.Product;
import com.spring.batch.scaling.repository.ProductRepository;

/**
 * com.spring.batch.scaling.multithreadedstep.ProcessIndicatorConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 25. 오후 7:03
 */
@Configuration
@EnableBatchProcessing
@Import({ JpaH2Configuration.class })
public class ProcessIndicatorConfiguration extends AbstractBatchConfiguration {

	@Autowired javax.persistence.EntityManagerFactory emf;
	@Autowired ProductRepository productRepository;

	@Bean
	public Job processIndicatorJob() {
		Step step = stepBuilders.get("processIndicatorStep")
		                        .<Product, Product>chunk(20)
		                        .reader(productItemReader())
		                        .writer(productItemWriter())
		                        .taskExecutor(taskExecutor())
		                        .build();
		return jobBuilders.get("processIndicatorJob").start(step).build();
	}

	@Bean
	public SynchronizingItemReader productItemReader() {
		SynchronizingItemReader reader = new SynchronizingItemReader();
		reader.setDelegate(targetProductItemReader());
		return reader;
	}

	@Bean
	public JpaPagingItemReader<Product> targetProductItemReader() {
		JpaPagingItemReader<Product> reader = new JpaPagingItemReader<>();

		// NOTE:chuck size와 같거나 작아야 한다.
		reader.setPageSize(10);
		reader.setSaveState(false);
		reader.setTransacted(false);

		reader.setEntityManagerFactory(emf);
		reader.setQueryString("select p from Product p where p.processed=false");

		return reader;
	}

	@Bean
	public ProductItemWriter productItemWriter() {
		ProductItemWriter writer = new ProductItemWriter();
		writer.setProductRepository(productRepository);
		return writer;
	}
}
