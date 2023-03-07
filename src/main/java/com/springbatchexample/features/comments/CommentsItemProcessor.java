package com.springbatchexample.features.comments;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;

public class CommentsItemProcessor implements ItemProcessor<Comment, Comment> {

    @Override
    @Nullable
    public Comment process(Comment item) throws Exception {
        return item;
    }

}
