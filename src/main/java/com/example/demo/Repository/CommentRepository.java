package com.example.demo.Repository;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Iterable<Comment> getAllByPost(Post post);
}

