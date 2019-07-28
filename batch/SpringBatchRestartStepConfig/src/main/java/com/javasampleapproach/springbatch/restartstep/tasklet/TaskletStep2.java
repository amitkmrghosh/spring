package com.javasampleapproach.springbatch.restartstep.tasklet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskletStep2 implements Tasklet {
	
	//@Value("${filepath}")
	//private String FILE_PATH;
	
	private String FILE_PATH = "/Users/amitkumarghosh/Downloads/spring_downloads/SpringBatchRestartStepConfig/src/test/resources/input/1.txt";
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		System.out.println("### Step 2 - Processing!");
		
		if(!new File(FILE_PATH).exists()){
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
				StringBuffer content = new StringBuffer();
				content.append("Line 1\n");
				content.append("Line 2\n");
				content.append("Line 3\n");
				content.append("Line 4\n");
				content.append("Line 5\n");
				bw.write(content.toString());
				System.out.println("Done");
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		return null;
	}
}