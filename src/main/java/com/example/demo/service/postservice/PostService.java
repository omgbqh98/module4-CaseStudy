package com.example.demo.service.postservice;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.GeneralService;

public interface PostService extends GeneralService<Post> {
    Iterable<Post> findAllByCategory(Category category);

    Iterable<Post> getAllByOrderByDateDesc();

    Iterable<Post> getAllUserOrderByDateDesc(User user);

    Post getByPost_id(Long id);

    void delete(Post post);

    Long countPost();

//    Post findByPost_Id(Long id);

}
