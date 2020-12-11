package com.example.demo.Repository;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    Iterable<Post> getAllByOrderByDateDesc();

    Iterable<Post> getAllByUserOrderByDateDesc(User user);

    Iterable<Post> findAllByCategory(Category category);

    //    Post findByPost_id(Long id);
//    Post findAllByPost_id(Long id);
}
