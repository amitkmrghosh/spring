package com.javasampleapproach.springbatch.restartstep;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import com.javasampleapproach.springbatch.restartstep.tasklet.TaskletStep1;
import com.javasampleapproach.springbatch.restartstep.tasklet.TaskletStep2;
import com.javasampleapproach.springbatch.restartstep.tasklet.TaskletStep3;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;


    @Bean
    public Job testJob() {

        return (jobBuilderFactory.get("testJob1")
                .incrementer(new RunIdIncrementer())
                .start(taskletStep1())
                .next(taskletStep2())
                .next(taskletStep3())
                .build());

    }

    @Bean
    public Step taskletStep1() {
        return stepBuilderFactory.get("taskletStep1").tasklet(new TaskletStep1()).build();

    }
    
    @Bean
    public Step taskletStep2() {
        return stepBuilderFactory.get("taskletStep2").tasklet(new TaskletStep2()).allowStartIfComplete(true).build();

    }
    
    @Bean
    public Step taskletStep3() {
        return stepBuilderFactory.get("taskletStep3").tasklet(new TaskletStep3()).startLimit(3).build();

    }
    
    @Bean(name = "jobDataSource")
    public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setName("JobRepository")
				.setType(EmbeddedDatabaseType.H2)
				.addScript("classpath:/org/springframework/batch/core/schema-drop-h2.sql")
				.addScript("classpath:/org/springframework/batch/core/schema-h2.sql")
				.build();
	}

    @Bean(name = "jobTransactionManager")
	public PlatformTransactionManager jobTransactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean(name = "jobTaskExecutor")
	public TaskExecutor jobTaskExecutor() throws Exception {

		// NOTE: TaskExecutor 를 제공하는 것은 비동기 방식으로 작업을 수행한다는 뜻입니다.
		// NOTE: 테스트 시에는 작업 진행 중에 테스트가 종료될 수 있으므로 옳바른 테스트 결과가 안나올 수 있습니다.
		// HINT: 테스트 시에는 null 을 주시면 동기방식으로 처리되어 모든 작업이 끝나야 테스트 메소드가 종료됩니다.

//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setMaxPoolSize(32);
//		executor.afterPropertiesSet();
//		return executor;
		return null;
	}
    
    
}
