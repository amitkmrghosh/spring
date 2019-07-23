package com.amit.test.jdbc;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.test.context.ContextConfiguration;

import com.amit.test.AbstractJobStructureTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ContextConfiguration(classes = { JobStructureJdbcConfig.class })
public class JobStructureJdbcTest extends AbstractJobStructureTest {

	@Ignore
	@Test
	public void jdbcPagingJob() throws Exception {
		jobLauncher.run(job, new JobParameters());
		checkProducts(writer.getProducts(),
		              new String[] {
				              "PR....210",
				              "PR....211",
				              "PR....212",
				              "PR....213",
				              "PR....214",
				              "PR....215",
				              "PR....216",
				              "PR....217"
		              });
	}
}
