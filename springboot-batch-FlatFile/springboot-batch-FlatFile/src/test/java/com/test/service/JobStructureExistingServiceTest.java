package com.test.service;

import org.junit.Test;
import org.springframework.batch.core.JobParameters;
import org.springframework.test.context.ContextConfiguration;

import com.amit.test.AbstractJobStructureTest;



@ContextConfiguration(classes = { JobStructureExistingServiceConfig.class })
public class JobStructureExistingServiceTest extends AbstractJobStructureTest {

	@Test
	public void existingServiceJob() throws Exception {
		jobLauncher.run(job, new JobParameters());

		checkProducts(writer.getProducts(), new String[] { "PR....210",
				"PR....211",
				"PR....212",
				"PR....213",
				"PR....214",
				"PR....215",
				"PR....216",
				"PR....217" });
	}
}
