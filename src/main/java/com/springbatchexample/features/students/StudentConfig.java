package com.springbatchexample.features.students;

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
public class StudentConfig implements JobConfig<StudentItemProcessor, Student> {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final StudentRepository studentRepository;

    private final APIClient apiClient;

    @Override
    public JsonItemReader<Student> jsonItemReader() {
        return new JsonItemReaderBuilder<Student>()
                .jsonObjectReader(new CustomJsonReader<>(Student.class))
                .resource(apiClient.getUsers())
                .name("studentJsonItemReader")
                .build();
    }

    @Override
    public StudentItemProcessor processor() {
        return new StudentItemProcessor();
    }

    @Override
    public ItemWriter<Student> writer() {
        return studentRepository::saveAll;
    }

    @Override
    @Bean(name = "STUDENT_JOB")
    public Job writeEntityToDb() {
        JobBuilder jobBuilder = jobBuilderFactory.get("STUDENT_JOB");
        jobBuilder.incrementer(new RunIdIncrementer());
        FlowJobBuilder flowJobBuilder = jobBuilder.flow(getStep()).end();
        Job job = flowJobBuilder.build();
        return job;
    }

    @Override
    public Step getStep() {
        StepBuilder stepBuilder = stepBuilderFactory.get("getFirstStep");
        SimpleStepBuilder<Student, Student> simpleStepBuilder = stepBuilder.chunk(1);
        return simpleStepBuilder.reader(jsonItemReader()).processor(processor()).writer(writer()).build();
    }

}
