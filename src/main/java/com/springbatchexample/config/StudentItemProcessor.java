package com.springbatchexample.config;

import org.springframework.batch.item.ItemProcessor;

import com.springbatchexample.entity.Student;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {

    @Override
    public Student process(Student student) throws Exception {
        return student;
    }
}
