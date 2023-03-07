package com.springbatchexample.features.comments;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatchexample.config.CustomJsonReader;
import com.springbatchexample.config.JobConfig;
import com.springbatchexample.features.common.APIClient;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CommentsConfig implements JobConfig<CommentsItemProcessor, Comment> {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final CommentRepository commentRepository;

    private final APIClient apiClient;

    public JsonItemReader<Comment> jsonItemReader() {
        return new JsonItemReaderBuilder<Comment>()
                .jsonObjectReader(new CustomJsonReader<>(Comment.class))
                .resource(apiClient.getComments())
                .name("commentsJsonReader")
                .build();
    }

    public CommentsItemProcessor processor() {
        return new CommentsItemProcessor();
    }

    public ItemWriter<Comment> writer() {
        return commentRepository::saveAll;
    }

    @Bean(name = "COMMENTS_JOB")
    public Job writeEntityToDb() {
        JobBuilder jobBuilder = jobBuilderFactory.get("COMMENTS_JOB");
        jobBuilder.incrementer(new RunIdIncrementer());
        FlowJobBuilder flowJobBuilder = jobBuilder.flow(getStep()).end();
        Job job = flowJobBuilder.build();
        return job;
    }

    public Step getStep() {
        StepBuilder stepBuilder = stepBuilderFactory.get("getNextStep");
        SimpleStepBuilder<Comment, Comment> simpleStepBuilder = stepBuilder.chunk(1);
        return simpleStepBuilder.reader(jsonItemReader()).processor(processor()).writer(writer()).build();
    }

}
