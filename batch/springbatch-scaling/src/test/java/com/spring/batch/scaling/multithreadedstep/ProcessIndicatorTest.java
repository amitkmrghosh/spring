package com.spring.batch.scaling.multithreadedstep;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.batch.scaling.domain.Product;
import com.spring.batch.scaling.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * com.spring.batch.scaling.multithreadedstep.ProcessIndicatorTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 20. 오후 2:55
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration({ "classpath:/com/spring/batch/scaling/multithreadedstep/process-indicator-context.xml" })
@ContextConfiguration(classes = { ProcessIndicatorConfiguration.class })
public class ProcessIndicatorTest {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job processIndicatorJob;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	TaskExecutor taskExecutor;

	@PersistenceContext
	EntityManager em;

	@Before
	public void initializeDatabase() {
		int count = 55;
		for (int i = 0; i < count; i++) {
			Product p = new Product(String.valueOf(i));
			p.setName("Proudct " + i);
			p.setPrice(124.60f);
			productRepository.save(p);
		}
		productRepository.flush();
	}

	@Test
	public void multiThreadedStep() throws Exception {
		JobExecution multiThreadedJobExec = jobLauncher.run(processIndicatorJob, new JobParameters());

		Assertions.assertThat(multiThreadedJobExec.getStatus()).isEqualTo(BatchStatus.COMPLETED);

		long countNotProcessed = productRepository.countByNotProcessed();
		Assertions.assertThat(countNotProcessed).isEqualTo(0);
	}

}
