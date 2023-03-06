package com.springbatchexample.features.students;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.springbatchexample.config.WithJson;

import lombok.Data;

@Data
@Entity
public class Student implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @WithJson("$.id")
    private Integer things;

    @WithJson("$.name.first")
    private String name;

    @WithJson("$.rollNumber")
    private String rollNumber;

}