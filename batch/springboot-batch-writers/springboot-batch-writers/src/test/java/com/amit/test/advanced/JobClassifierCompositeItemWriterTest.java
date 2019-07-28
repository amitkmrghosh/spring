package com.amit.test.advanced;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amit.model.Product;
import com.amit.repository.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JobClassifierCompositeItemWriterConfiguration.class })
public class JobClassifierCompositeItemWriterTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Autowired
	private ProductRepository repository;

	@Before
	public void setup() {
		repository.deleteAll();
		repository.flush();

		repository.save(new Product("PR....211", "Sony Ericsson W810i", "", new BigDecimal(139.45), ""));
		repository.save(new Product("PR....212", "Samsung MM-A900M Ace", "", new BigDecimal(97.80), ""));
		repository.flush();
	}

	@Test
	public void testClassifier() throws Exception {
		JobExecution exec = jobLauncherTestUtils.launchJob();
		Assertions.assertThat(exec.getStatus()).isEqualTo(BatchStatus.COMPLETED);

		// 미리 2개 넣고, 8건 추가 시 삭제 1건, Update 1건, 6건 추가 => 7건 남음
		Assertions.assertThat(repository.count()).isEqualTo(7);
	}
}
