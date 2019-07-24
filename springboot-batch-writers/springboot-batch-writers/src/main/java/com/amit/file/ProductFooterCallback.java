package com.amit.file;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.file.FlatFileFooterCallback;

import java.io.IOException;
import java.io.Writer;

public class ProductFooterCallback extends StepExecutionListenerSupport implements FlatFileFooterCallback {

	private static final String LINE_SEPARATOR = System.lineSeparator();
	private StepExecution stepExecution;

	public void writeFooter(Writer writer) throws IOException {
		writer.write("# Write count: " + stepExecution.getWriteCount());
		writer.write(LINE_SEPARATOR);
		long delta = stepExecution.getEndTime().getTime() - stepExecution.getStartTime().getTime();
		writer.write("# Done in: " + delta + " ms");
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
}
