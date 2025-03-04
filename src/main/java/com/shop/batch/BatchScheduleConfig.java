package com.shop.batch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class BatchScheduleConfig {

    private final JobRegistry jobRegistry;
    private final JobLauncher jobLauncher;

    //lockAtMostFor: 락이 유지되는 최대 시간, lockAtLeastFor: 락이 유지되는 최소 시간
    //@SchedulerLock(name = "paymentBatchJob", lockAtMostFor = "PT9S", lockAtLeastFor = "PT9S")
    //@Scheduled(cron = "*/10 * * * * *", zone = "Asia/Seoul")
    public void schedule() throws Exception {
        //log.info("Schedule Batch Job");
        log.info("Starting schedule at {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        //Job job = jobRegistry.getJob("paymentBatchJob");
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        //String date = dateFormat.format(new Date());
        //
        //JobParameters jobParameters = new JobParametersBuilder()
        //        .addString("date", date)
        //        .toJobParameters();
        //
        //jobLauncher.run(job, jobParameters);
    }
}
