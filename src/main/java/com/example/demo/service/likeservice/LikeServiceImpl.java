package com.example.demo.service.likeservice;

import com.example.demo.Repository.LikeRepository;
import com.example.demo.model.LikePost;
import com.example.demo.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    LikeRepository likeRepository;

    @Override
    public Long countLike(Post post) {
        return likeRepository.countAllByPost(post);
    }

    @Override
    public Long countAllLike() {
        return likeRepository.countAllBy();
    }

    @Override
    public Iterable<LikePost> findAll() {
        return likeRepository.findAll();
    }

    @Override
    public Optional<LikePost> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public LikePost save(LikePost like) {
        return likeRepository.save(like);
    }

    @Override
    public void remove(Long id) {

    }
}
