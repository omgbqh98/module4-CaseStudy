package com.example.demo.service.postservice;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService extends GeneralService<Post> {
    Iterable<Post> findAll();
    
    Iterable<Post> getAllPostByUserId(Long userId);

    Iterable<Post> findAllByCategory(Category category);

    Iterable<Post> getAllByOrderByCountLikeDesc();
    
    Iterable<Post> getAllByOrderByDateDesc();

    Iterable<Post> getAllUserOrderByDateDesc(User user);

    Page<Post> findAllPostByTitleAndUserId(String title, Pageable pageable, Long userId);

    Page<Post> findAllPostPageableWithUserId(Pageable pageable, Long userId);

    Post getByPost_id(Long id);

    void delete(Post post);

    Long countPost();

    Long countPostByUserId(Long id);

    Iterable<Post> findByTitleContaining(String title);
//    Post findByPost_Id(Long id);
}