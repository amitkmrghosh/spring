package com.spring.batch.scaling.multithreadedstep;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.batch.scaling.AbstractBatchConfiguration;
import com.spring.batch.scaling.DummyProductReader;
import com.spring.batch.scaling.DummyProductWriter;
import com.spring.batch.scaling.domain.Product;

/**
 * com.spring.batch.scaling.multithreadedstep.MultithreadedStepConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 25. 오후 6:38
 */
@Configuration
@EnableBatchProcessing
public class MultithreadedStepConfiguration extends AbstractBatchConfiguration {

	@Bean
	public Job readWriteMultiThreadJob(DummyProductReader reader) {
		Step step = stepBuilders.get("readWriteMultiThreadedStep")
		                        .<Product, Product>chunk(10)
		                        .reader(reader)
		                        .writer(writer())
		                        .taskExecutor(taskExecutor())
		                        .build();
		return jobBuilders.get("readWriteMultiThreadedJob").start(step).build();
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
