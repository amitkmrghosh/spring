package com.amit;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.batch.core.annotation.OnWriteError;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class MyJob1 {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public ItemReader<Integer> itemReader() {
        return new ListItemReader<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Bean
    public ItemWriter<Integer> itemWriter() {
        return items -> {
            for (Integer item : items) {
                if (item.equals(3)) {
                    throw new Exception("No 3 here!");
                }
                System.out.println("item = " + item);
            }
        };
    }

    @Bean
    public Step step() {
        return steps.get("step")
                .<Integer, Integer>chunk(5)
                .reader(itemReader())
                .writer(itemWriter())
                .faultTolerant()
                .skip(Exception.class)
                .skipLimit(10)
                .listener(new StepExecutionListener())
                .build();
    }

    @Bean
    public Job job() {
        return jobs.get("job")
                .start(step())
                .build();
    }

    public class StepExecutionListener {

        @OnSkipInRead
        public void onSkipInRead(Throwable t) {
            System.err.println("On Skip in Read Error : " + t.getMessage());
        }

        @OnSkipInWrite
        public void onSkipInWrite(Integer item, Throwable t) {
            System.err.println("Skipped in write due to : " + t.getMessage());
        }

        @OnSkipInProcess
        public void onSkipInProcess(Integer item, Throwable t) {
            System.err.println("Skipped in process due to: " + t.getMessage());
        }

        @OnWriteError
        public void onWriteError(Exception exception, List<? extends Integer> items) {
            System.err.println("Error on write on " + items + " : " + exception.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyJob1.class);
        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean(Job.class);
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        System.out.println("WriteSkipCount = " + stepExecution.getWriteSkipCount());
    }

}
