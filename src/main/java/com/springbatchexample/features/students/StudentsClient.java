package com.springbatchexample.features.students;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://jsonplaceholder.typicode.com", value = "students-client", configuration = StudentsClientConfig.class)
public interface StudentsClient {

    @GetMapping(path = "/users", consumes = "application/json")
    public ByteArrayResource getUsers();

}
