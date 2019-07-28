package kr.spring.batch.chapter04.test.web;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;

import java.util.Date;

/**
 * kr.spring.batch.chapter04.test.web.SimpleLauncher
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 31. 오후 6:45
 */
public class SimpleLauncher {

     private JobLauncher jobLauncher;
     private Job job;

    public void launch() throws Exception {
        jobLauncher.run(job, new JobParametersBuilder().addDate("date", new Date()).toJobParameters());
    }
}
