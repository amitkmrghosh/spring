package com.spring.batch.scaling.parallel;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.spring.batch.scaling.AbstractBatchConfiguration;
import com.spring.batch.scaling.JpaH2Configuration;

/**
 * com.spring.batch.scaling.parallel.ParallelStepConfiguration
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 25. 오후 7:38
 */
@Configuration
@EnableBatchProcessing
@Import({ JpaH2Configuration.class })
@ImportResource({ "classpath:com/spring/batch/scaling/parallel/parallel-step-context.xml" })
public class ParallelStepConfiguration extends AbstractBatchConfiguration {
}
