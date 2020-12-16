package com.example.demo.Repository;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    Iterable<Post> getAllByUserId(Long userId);

    Iterable<Post> getAllByOrderByDateDesc();

    Iterable<Post> getAllByUserOrderByDateDesc(User user);

    Page<Post> findAllByTitleContaining(String title, Pageable pageable, Long userId);

    Page<Post> findAllByUserId(Pageable pageable, Long userId);

    Iterable<Post> findAllByCategory(Category category);

    Iterable<Post> findByTitleContaining(String title);

    Long countPostBy();

    Long countAllByUserId(Long id);



}
