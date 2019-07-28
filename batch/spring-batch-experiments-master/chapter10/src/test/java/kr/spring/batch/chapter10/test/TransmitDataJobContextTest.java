package kr.spring.batch.chapter10.test;

import kr.spring.batch.config.AbstractJobConfiguration;
import org.junit.runner.RunWith;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * kr.spring.batch.chapter10.test.TransmitDataJobContextTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 17. 오전 11:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({ "classpath:kr/spring/batch/chapter10/batch-infrastructure.xml",
//		                      "classpath:kr/spring/batch/chapter10/transmit-data-job-context-job.xml" })
@ContextConfiguration(classes = { TransmitDataJobContextTest.JobConfiguration.class })
public class TransmitDataJobContextTest extends AbstractJobTest {


	@Override
	protected void assertMetadataDownloadFileOkNoSkippedItems() {
		assertThat(importMetadataHolder.get()).isNull();
	}

	@Override
	protected void assertMetadataDownloadFileOkSkippedItems() {
		assertThat(importMetadataHolder.get()).isNull();
	}

	@Override
	protected void assertMetadataNoDownloadFile() {
		assertThat(importMetadataHolder.get()).isNull();
	}

	@Configuration
	@EnableBatchProcessing
	@ImportResource({ "classpath:kr/spring/batch/chapter10/transmit-data-job-context-job.xml" })
	public static class JobConfiguration extends AbstractJobConfiguration {
	}
}
