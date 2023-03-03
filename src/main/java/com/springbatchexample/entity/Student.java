package com.springbatchexample.entity;

import com.springbatchexample.main.WithJson;

import lombok.Data;

@Data
public class Student {

    @WithJson("$.id")
    private Integer id;

    @WithJson("$.name.first")
    private String name;

    @WithJson("$.rollNumber")
    private String rollNumber;

}