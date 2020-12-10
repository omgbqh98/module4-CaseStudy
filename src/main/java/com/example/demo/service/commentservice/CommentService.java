package com.example.demo.service.commentservice;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.service.GeneralService;

public interface CommentService extends GeneralService<Comment> {
    Iterable<Comment> getAllByPost(Post post);
}
