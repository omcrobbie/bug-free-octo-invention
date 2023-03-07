package com.springbatchexample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JsonItemReader;

public interface JobConfig<Processor, Entity> {

    JsonItemReader<Entity> jsonItemReader();

    Processor processor();

    ItemWriter<Entity> writer();

    Job writeEntityToDb();

    Step getStep();

}