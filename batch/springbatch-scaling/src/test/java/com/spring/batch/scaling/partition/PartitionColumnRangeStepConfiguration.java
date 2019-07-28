package com.spring.batch.scaling.partition;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import com.spring.batch.scaling.AbstractBatchConfiguration;
import com.spring.batch.scaling.JpaH2Configuration;


@Configuration
@EnableBatchProcessing
@Import({ JpaH2Configuration.class })
@ImportResource({ "classpath:com/spring/batch/scaling/partition/partition-context.xml" })
public class PartitionColumnRangeStepConfiguration extends AbstractBatchConfiguration {

}
