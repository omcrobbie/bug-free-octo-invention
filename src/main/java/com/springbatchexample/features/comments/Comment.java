package com.springbatchexample.features.comments;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.springbatchexample.config.WithJson;
import com.springbatchexample.features.common.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "COMMENTS")
public class Comment extends BaseEntity {

    @WithJson("$.postId")
    private Integer postId;

    @WithJson("$.id")
    private Integer userId;

    @WithJson("$.body")
    @Column(columnDefinition = "TEXT")
    private String body;

}
