package com.openbox;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SpringBootApplication
@EnableBatchProcessing
@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableRetry
public class Stack56170179Application {

	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	public static void main(final String[] args) {
		SpringApplication.run(Stack56170179Application.class, args);
	}

	@Bean
	public Job myJob(final Step step1) {
		return jobs.get("myJob").start(step1).build();
	}

	@Bean
	public Step step1(final ItemReader<String> myReader, final ItemProcessor<String, String> myProcessor,
			final ItemWriter<String> myWriter) {
		return steps.get("step1").<String, String>chunk(1).reader(myReader).processor(myProcessor).writer(myWriter)
				.build();
	}

	@Bean
	@StepScope
	public MyReader myReader() {
		return new MyReader();
	}

	@Bean
	public MyProcessor myProcessor() {
		return new MyProcessor();
	}

	@Bean
	public MyWriter myWriter() {
		return new MyWriter();
	}

}
