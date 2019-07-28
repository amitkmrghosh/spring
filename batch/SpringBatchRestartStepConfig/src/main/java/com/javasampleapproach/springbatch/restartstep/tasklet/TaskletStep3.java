package com.javasampleapproach.springbatch.restartstep.tasklet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

public class TaskletStep3 implements Tasklet {

	@Value("${sleeptime}")
    private Integer SLEEP_TIME;
	
	//@Value("${filepath}")
	private String FILE_PATH="/Users/amitkumarghosh/Downloads/spring_downloads/SpringBatchRestartStepConfig/src/test/resources/input/1.txt";
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		// SLEEP 10s for simulation a heavy processing
		Thread.sleep(SLEEP_TIME);

		System.out.println("### Step 3 - Processing!");
		try (Stream<String> stream = Files.lines(Paths.get(FILE_PATH))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            throw(e);
        }
		
		return null;
	}
}