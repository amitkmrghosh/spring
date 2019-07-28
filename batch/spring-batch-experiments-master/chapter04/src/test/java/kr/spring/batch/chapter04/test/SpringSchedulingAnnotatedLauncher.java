package kr.spring.batch.chapter04.test;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * kr.spring.batch.chapter04.test.SpringSchedulingAnnotatedLauncher
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 7. 31. 오후 4:51
 */
public class SpringSchedulingAnnotatedLauncher {

     Job job;

     JobLauncher jobLauncher;

    @Scheduled(fixedRate = 1000)
    public void launch() throws Exception {
        JobParameters jobParameters = createJobParameters();
        jobLauncher.run(job, jobParameters);
    }

    private JobParameters createJobParameters() {
        return new JobParametersBuilder()
            .addDate("date", new Date())
            .toJobParameters();
    }
}
