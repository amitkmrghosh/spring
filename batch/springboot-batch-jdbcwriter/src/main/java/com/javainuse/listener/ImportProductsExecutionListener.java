package com.javainuse.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ImportProductsExecutionListener implements StepExecutionListener {

	Logger log = LoggerFactory.getLogger(ImportProductsExecutionListener.class);
	
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		log.info("스텝 실행 전에 호출되는 리스너의 메소드입니다.");
	}

	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("스텝 완료 후 호출됩니다. StepName=[{}], ExitStatus=[{}]",
		         stepExecution.getStepName(), stepExecution.getExitStatus());

		return ExitStatus.COMPLETED;
	}
}
