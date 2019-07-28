package com.amit.test.jdbc;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.amit.jdbc.ProductRowMapper;
import com.amit.model.Product;
import com.amit.test.AbstractBatchConfiguration;
import com.amit.test.DummyProductItemWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableBatchProcessing
@EnableTransactionManagement
public class JobStructureJdbcConfig extends AbstractBatchConfiguration {

	@Bean(name = "productDataSource")
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setName("ProductDB")
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:create-tables.sql")
				.build();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

	@Bean
	public Job importProductsJob(ItemReader<Product> reader,
	                             ItemWriter<Product> writer) throws Exception {

		Step step = stepBuilders.get("importProductsJob")
		                        .<Product, Product>chunk(100)
		                        .reader(reader)
		                        .writer(writer)
		                        .build();

		return jobBuilders.get("importProductsJob")
		                  .start(step)
		                  .build();
	}

	@Bean
	public ItemReader<Product> productItemReader(@Qualifier("productDataSource") DataSource dataSource,
	                                             PagingQueryProvider productQueryProvider) throws Exception {
		JdbcPagingItemReader<Product> reader = new JdbcPagingItemReader<Product>();

		reader.setDataSource(dataSource);
		reader.setQueryProvider(productQueryProvider);
		reader.setPageSize(5);
		reader.setRowMapper(productRowMapper());
		reader.afterPropertiesSet();

		return reader;
	}

	@Bean
	public PagingQueryProvider productQueryProvider(@Qualifier("productDataSource") DataSource dataSource) throws Exception {
		SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
		factoryBean.setDataSource(dataSource);
		factoryBean.setSelectClause("select id, name, description, price");
		factoryBean.setFromClause("from product");
		factoryBean.setSortKey("id");

		return (PagingQueryProvider) factoryBean.getObject();
	}

	@Bean
	public RowMapper<Product> productRowMapper() {
		return new ProductRowMapper();
	}

	@Bean
	public ItemWriter<Product> productItemWriter() {
		return new DummyProductItemWriter();
	}
}

