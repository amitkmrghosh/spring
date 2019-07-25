package com.amit.test.skip;


import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amit.jpa.repositories.ProductRepository;
import com.amit.jpa.repositories.SkippedProductRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SkipConfiguration.class })
public class SkipTest {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	private Job importProductsJob;

	@Autowired
	private Job importProductsJobWithSkipPolicy;

	@Test
	public void jobWithNoSkip() throws Exception {
		int initialCount = countProducts();
		int initialSkippedCount = countSkippedProducts();

		JobParameters params = new JobParametersBuilder()
				.addString("inputFile", "classpath:skip/products_no_error.txt")
				.toJobParameters();

		JobExecution exec = jobLauncher.run(importProductsJob, params);

		Assertions.assertThat(exec.getStatus()).isEqualTo(BatchStatus.COMPLETED);
		Assertions.assertThat(countProducts()).isEqualTo(initialCount + 8);
		Assertions.assertThat(getStepExec(exec).getSkipCount()).isEqualTo(0);
		Assertions.assertThat(getStepExec(exec).getRollbackCount()).isEqualTo(0);
		Assertions.assertThat(countSkippedProducts()).isEqualTo(initialSkippedCount + getStepExec(exec).getSkipCount());
	}


	@Autowired
	ProductRepository productRepository;

	@Autowired
	SkippedProductRepository skippedProductRepository;

	private int countProducts() {
		return Long.valueOf(productRepository.count()).intValue();
	}

	private int countSkippedProducts() {
		return Long.valueOf(skippedProductRepository.count()).intValue();
	}

	private StepExecution getStepExec(JobExecution exec) {
		return exec.getStepExecutions().iterator().next();
	}
}
