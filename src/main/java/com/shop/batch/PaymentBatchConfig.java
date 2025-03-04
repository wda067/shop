package com.shop.batch;

import com.shop.repository.PaymentRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PaymentBatchConfig {

    private static final String JOB_NAME = "paymentBatchJob";

    private final PaymentRepository paymentRepository;

    //Spring Batch의 배치 작업 단위 -> Job
    @Bean
    public Job paymentJob(JobRepository jobRepository, Step paymentStep) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .start(paymentStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    //배치 처리는 Job 아래에 Step에서 수행된다.
    @Bean
    public Step paymentStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("paymentStep", jobRepository)
                .tasklet(paymentTasklet(), transactionManager)
                .allowStartIfComplete(true)  //완료되었더라도 반복 실행
                .build();
    }

    //Tasklet -> 배치 작업에서 단일 작업을 처리하기 위한 인터페이스
    @Bean
    public Tasklet paymentTasklet() {
        return (contribution, chunkContext) -> {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            long paymentCount = paymentRepository.countByRequestedAt(yesterday);
            log.info("{} 결제 건수: {}", yesterday.format(DateTimeFormatter.ISO_DATE), paymentCount);
            return RepeatStatus.FINISHED;
        };
    }
}
