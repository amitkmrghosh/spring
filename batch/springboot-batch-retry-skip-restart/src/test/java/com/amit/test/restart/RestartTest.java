package com.amit.test.restart;

import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * kr.spring.batch.chapter08.test.restart.RestartTest
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 14. 오전 10:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestartConfiguration.class })
public class RestartTest {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private ItemWriter<File> writer;

    private JobParameters jobParameters =
        new JobParametersBuilder()
            .addLong("date", System.currentTimeMillis())
            .toJobParameters();

    @Test
    public void restart() throws Exception {
        doNothing().doThrow(new RuntimeException()).when(writer).write(anyList());

        JobExecution exec = jobLauncher.run(job, jobParameters);
        Assertions.assertThat(exec.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());

        ArgumentCaptor<List> captor = ArgumentCaptor.forClass(List.class);
        verify(writer, times(2)).write(captor.capture());

        List<List> files = captor.getAllValues();

        List<?> written = files.get(0);
        Assertions.assertThat(written.size()).isEqualTo(3);

        List<?> rolledback = files.get(1);
        Assertions.assertThat(rolledback.size()).isEqualTo(3);

        Assertions.assertThat(extractFilenames(written).toString()).isEqualTo("[01, 02, 03]");
        Assertions.assertThat(extractFilenames(rolledback).toString()).isEqualTo("[04, 05, 06]");

        StepExecution stepExec = exec.getStepExecutions().iterator().next();
        System.out.println(stepExec.getExecutionContext());

        reset(writer);
        doNothing().doThrow(new RuntimeException()).when(writer).write(anyList());

        exec = jobLauncher.run(job, jobParameters);
        Assertions.assertThat(exec.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode());

        captor = ArgumentCaptor.forClass(List.class);
        verify(writer, times(2)).write(captor.capture());

        files = captor.getAllValues();

        written = files.get(0);
        Assertions.assertThat(written.size()).isEqualTo(3);

        rolledback = files.get(1);
        Assertions.assertThat(rolledback.size()).isEqualTo(3);

        Assertions.assertThat(extractFilenames(written).toString()).isEqualTo("[04, 05, 06]");
        Assertions.assertThat(extractFilenames(rolledback).toString()).isEqualTo("[07, 08, 09]");

        reset(writer);
        doNothing().when(writer).write(anyList());

        exec = jobLauncher.run(job, jobParameters);
        Assertions.assertThat(exec.getExitStatus().getExitCode()).isEqualTo(ExitStatus.COMPLETED.getExitCode());

        captor = ArgumentCaptor.forClass(List.class);
        verify(writer, times(2)).write(captor.capture());

        files = captor.getAllValues();

        written = files.get(0);
        Assertions.assertThat(written.size()).isEqualTo(3);

        rolledback = files.get(1);
        Assertions.assertThat(rolledback.size()).isEqualTo(1);

        Assertions.assertThat(extractFilenames(written).toString()).isEqualTo("[07, 08, 09]");
        Assertions.assertThat(extractFilenames(rolledback).toString()).isEqualTo("[10]");
    }

    private List<String> extractFilenames(List files) {
        List<String> r = new ArrayList<String>();
        for (Object obj : files) {
            File file = (File) obj;
            // path, extension을 제외한 순수 파일명만 제공
            r.add(FilenameUtils.getBaseName(file.getName()));
        }
        return r;
    }

}
