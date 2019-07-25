package com.amit.test.restart;

import java.io.File;

import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.amit.jpa.repositories.ProductRepository;
import com.amit.restart.FilesInDirectoryItemReader;
import com.amit.test.BatchDataSourceConfiguration;
import com.amit.test.BatchInfrastructureConfiguration;
import com.amit.test.JpaH2Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * kr.spring.batch.chapter08.test.restart.RestartConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 14. 오전 11:02
 */
@Slf4j
@Configuration
@EnableAspectJAutoProxy
@EnableBatchProcessing
@EnableJpaRepositories(basePackageClasses = { ProductRepository.class })
@Import({ BatchInfrastructureConfiguration.class,
    BatchDataSourceConfiguration.class,
    JpaH2Configuration.class })
public class RestartConfiguration {

	@Autowired
	protected JobBuilderFactory jobBuilders;

	@Autowired
	protected StepBuilderFactory stepBuilders;

	@Bean
	public Job restartJob() {
		Step step = stepBuilders.get("restartStep")
		                        .<File, File>chunk(3)
		                        .reader(reader())
		                        .writer(writer())
		                        .build();

		return jobBuilders.get("restartJob")
		                  .start(step)
		                  .build();
	}

	@Bean
	public FilesInDirectoryItemReader reader() {
		FilesInDirectoryItemReader reader = new FilesInDirectoryItemReader();
		reader.setDirectory("./src/test/resources/restart/inputs");
		return reader;
	}

	@Bean
	@SuppressWarnings("unchecked")
	public ItemWriter<File> writer() {
		return (ItemWriter<File>) Mockito.mock(ItemWriter.class);
	}
}
