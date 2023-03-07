package com.springbatchexample.features.common;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;

import com.springbatchexample.config.FeignConfig;

@FeignClient(url = "https://jsonplaceholder.typicode.com", value = "api-client", configuration = FeignConfig.class)
public interface APIClient {

    @GetMapping(path = "/users", consumes = "application/json")
    public ByteArrayResource getUsers();

    @GetMapping(path = "/comments", consumes = "application/json")
    public ByteArrayResource getComments();
}
