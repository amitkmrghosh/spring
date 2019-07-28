package kr.spring.batch.chapter10.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * kr.spring.batch.chapter10.test.JobWithListenersTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 16. 오후 7:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { JobWithListenersConfiguration.class })
public class JobWithListenersTest extends AbstractJobTest {
}
