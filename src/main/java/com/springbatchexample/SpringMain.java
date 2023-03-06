package com.springbatchexample;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableBatchProcessing
@EnableFeignClients
@EnableJpaAuditing
public class SpringMain {
    public static void main(String[] args) {

        SpringApplication.run(SpringMain.class, args);
    }
}
