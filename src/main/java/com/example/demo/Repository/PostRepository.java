package com.example.demo.Repository;

import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import org.hibernate.sql.Select;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post, Long> {

    Iterable<Post> getAllByOrderByDateDesc();

    Iterable<Post> getAllByUserOrderByDateDesc(User user);

    Iterable<Post> findAllByCategory(Category category);


    Long countPostBy();

}
