package com.springbatchexample.features.students;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.springbatchexample.config.WithJson;
import com.springbatchexample.features.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "STUDENTS")
@EqualsAndHashCode(callSuper = false)
public class Student extends BaseEntity {

    @WithJson("$.email")
    private String email;

    @WithJson("$.id")
    private Integer userId;

    @WithJson("$.name")
    private String name;

    @WithJson("$.username")
    private String userName;

    @WithJson("$.company.name")
    private String companyName;

    private Integer postsCount;

}