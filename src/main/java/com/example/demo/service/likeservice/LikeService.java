package com.example.demo.service.likeservice;

import com.example.demo.model.LikePost;
import com.example.demo.model.Post;
import com.example.demo.service.GeneralService;
import org.springframework.stereotype.Service;

@Service
public interface LikeService extends GeneralService<LikePost> {
    Long countLike(Post post);

    Long countAllLike();
}
