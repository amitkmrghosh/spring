package com.amit.test.beans;

import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * kr.spring.batch.chapter08.test.beans.DummyItemReader
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 9. 오후 2:22
 */
@Slf4j
public class DummyItemReader implements ItemReader<String> {
	Logger log = LoggerFactory.getLogger(DummyItemReader.class);
    private BusinessService service;

    public DummyItemReader(BusinessService service) {
        this.service = service;
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        String read = service.reading();
        log.debug("read [{}]", read);
        return read;
    }
}
