package com.spring.batch.scaling.multithreadedstep;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.spring.batch.scaling.AbstractBatchConfiguration;
import com.spring.batch.scaling.DummyProductReader;
import com.spring.batch.scaling.DummyProductWriter;
import com.spring.batch.scaling.JpaH2Configuration;
import com.spring.batch.scaling.domain.Product;


@Configuration
@EnableBatchProcessing
@Import({ JpaH2Configuration.class })
public class MultithreadedStepThrottleLimitConfiguration extends AbstractBatchConfiguration {

	@Bean
	public Job readWriteMultiThreadJob(DummyProductReader reader) {
		Step step = stepBuilders.get("readWriteMultiThreadStep")
		                        .<Product, Product>chunk(10)
		                        .reader(reader)
		                        .writer(writer())
		                        .taskExecutor(taskExecutor())
		                        .throttleLimit(5)
		                        .build();

		return jobBuilders.get("readWriteMultiThreadJob").start(step).build();
	}

	@Bean
	@StepScope
	public DummyProductReader reader(@Value("#{jobParameters['count']}") Integer max) {
		return new DummyProductReader(max);
	}

	@Bean
	@StepScope
	public DummyProductWriter writer() {
		return new DummyProductWriter();
	}
}
