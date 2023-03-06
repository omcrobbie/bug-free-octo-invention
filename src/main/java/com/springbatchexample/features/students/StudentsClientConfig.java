package com.springbatchexample.features.students;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;

import feign.codec.Decoder;

public class StudentsClientConfig {

    @Bean
    public Decoder decoder() {
        return (response, type) -> {

            String dataStr = IOUtils.toString(response.body().asInputStream());
            return new ByteArrayResource(dataStr.getBytes());

        };

    }
}
