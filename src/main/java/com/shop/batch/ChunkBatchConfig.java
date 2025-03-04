package com.shop.batch;

import com.shop.domain.Member;
import com.shop.repository.member.MemberRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ChunkBatchConfig {

    private final MemberRepository memberRepository;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job chunkBatchJob() {
        return new JobBuilder("chunkBatchJob", jobRepository)
                .start(chunkStep())
                .build();
    }

    @Bean
    public Step chunkStep() {
        return new StepBuilder("chunkStep", jobRepository)
                .<Member, Member>chunk(10, transactionManager)
                .reader(memberReader())
                .processor(memberProcessor())
                .writer(memberWriter())
                .build();
    }

    @Bean
    public RepositoryItemReader<Member> memberReader() {
        return new RepositoryItemReaderBuilder<Member>()
                .name("memberReader")
                .pageSize(10)
                .methodName("findAll")
                .repository(memberRepository)
                .sorts(Map.of("id", Direction.ASC))
                .build();
    }

    @Bean
    public ItemProcessor<Member, Member> memberProcessor() {
        return member -> {
            log.info("Processing - 이메일: {}, 이름: {}", member.getEmail(), member.getName());
            return member;
        };
    }

    @Bean
    public ItemWriter<Member> memberWriter() {
        return members -> members.forEach(member -> {
            log.info("Writing - 이메일: {}, 이름: {}", member.getEmail(), member.getName());
        });
    }
}
