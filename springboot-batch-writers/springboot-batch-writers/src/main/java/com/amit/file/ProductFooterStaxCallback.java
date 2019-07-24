package com.amit.file;

import java.io.IOException;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.xml.StaxWriterCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductFooterStaxCallback extends StepExecutionListenerSupport implements StaxWriterCallback {

	private StepExecution stepExecution;

	@Override
	public void write(XMLEventWriter writer) throws IOException {
		try {
			XMLEventFactory eventFactory = XMLEventFactory.newInstance();

			XMLEvent event = eventFactory.createStartElement("", "", "footer");
			writer.add(event);

			event = eventFactory.createStartElement("", "", "writeCount");
			writer.add(event);

			event = eventFactory.createCharacters(String.valueOf(stepExecution.getWriteCount()));
			writer.add(event);

			event = eventFactory.createEndElement("", "", "writeCount");
			writer.add(event);

			event = eventFactory.createEndElement("", "", "footer");
			writer.add(event);
		} catch (XMLStreamException ignored) {
			//log.warn("Footer 작업 중 예외가 발생했습니다.", ignored);
		}
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
}
