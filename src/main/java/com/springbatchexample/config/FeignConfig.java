package com.springbatchexample.config;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;

import feign.codec.Decoder;

public class FeignConfig {

    @Bean
    public Decoder decoder() {
        return (response, type) -> {

            byte[] data = IOUtils.toByteArray(response.body().asInputStream());
            return new ByteArrayResource(data);

        };

    }
}
