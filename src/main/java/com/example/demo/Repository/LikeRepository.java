package com.example.demo.Repository;

import com.example.demo.model.LikePost;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends PagingAndSortingRepository<LikePost,Long> {
    Long countAllByPost(Post post);

    Iterable<LikePost> getByPost(Post post);

    Long countAllBy();

    Long countAllByUser(User user);
}
