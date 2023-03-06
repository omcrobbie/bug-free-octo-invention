package com.springbatchexample.features.students;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.springbatchexample.config.WithJson;

import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Student implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @CreatedDate
    private Date createdAt;

    @WithJson("$.email")
    private String email;

    @WithJson("$.name")
    private String name;

    @WithJson("$.username")
    private String userName;

    @WithJson("$.company.name")
    private String companyName;

}