package com.shop.batch;

import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BatchController {

    private final JobRegistry jobRegistry;
    private final JobLauncher jobLauncher;
    private final JobExplorer jobExplorer;

    @GetMapping("/run-job")
    public void runJob(@RequestParam("jobName") String jobName) throws Exception {
        Job job = jobRegistry.getJob(jobName);
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
        String date = dateFormat.format(new Date());

        //실행한 작업에 대한 일자 등을 부여해 동일한 일자에 대한 작업의 수행 여부를 확인하여 중복 실행 및 미실행을 방지
        JobParameters jobParameters = new JobParametersBuilder(jobExplorer)
                .getNextJobParameters(job)
                .addString("date", date)
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }

    @GetMapping("/tasklet")
    public void tasklet() throws Exception {
        Job job = jobRegistry.getJob("taskletBatchJob");
        jobLauncher.run(job, new JobParameters());
    }

    @GetMapping("/chunk")
    public void chunk() throws Exception {
        Job job = jobRegistry.getJob("chunkBatchJob");
        jobLauncher.run(job, new JobParameters());
    }
}
