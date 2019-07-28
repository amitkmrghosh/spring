package com.amit.test.file.xml;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;

import org.apache.commons.io.IOUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JobXmlMultiFileConfiguration.class })
public class JobXmlMultiFileTest {

	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;

	@Test
	public void testXml() throws Exception {
		JobExecution exec = jobLauncherTestUtils.launchJob();
		Assertions.assertThat(exec.getStatus()).isEqualTo(BatchStatus.COMPLETED);

		Resource ouput = new FileSystemResource(JobXmlMultiFileConfiguration.OUTPUT_FILE + ".1");
		String content = IOUtils.toString(ouput.getInputStream());
		assertXpathEvaluatesTo("1000", "count(//product)", content);

		ouput = new FileSystemResource(JobXmlMultiFileConfiguration.OUTPUT_FILE + ".2");
		content = IOUtils.toString(ouput.getInputStream());
		assertXpathEvaluatesTo("1000", "count(//product)", content);

		ouput = new FileSystemResource(JobXmlMultiFileConfiguration.OUTPUT_FILE + ".3");
		content = IOUtils.toString(ouput.getInputStream());
		assertXpathEvaluatesTo("1000", "count(//product)", content);

		ouput = new FileSystemResource(JobXmlMultiFileConfiguration.OUTPUT_FILE + ".4");
		content = IOUtils.toString(ouput.getInputStream());
		assertXpathEvaluatesTo("1000", "count(//product)", content);

		ouput = new FileSystemResource(JobXmlMultiFileConfiguration.OUTPUT_FILE + ".5");
		content = IOUtils.toString(ouput.getInputStream());
		assertXpathEvaluatesTo("517", "count(//product)", content);
	}
}
