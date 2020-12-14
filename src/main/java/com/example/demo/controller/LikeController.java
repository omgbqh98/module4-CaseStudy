package com.example.demo.controller;

import com.example.demo.model.Comment;
import com.example.demo.model.LikePost;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.likeservice.LikeService;
import com.example.demo.service.postservice.PostService;
import com.example.demo.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.Like;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

public class LikeController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    LikeService likeService;




    @ModelAttribute("user")
    public User getUserCurrent() {
        User userCurrent = userService.getCurrentUser();
        return userCurrent;
    }
    @ModelAttribute("post")
    public Post newPost() {
        Post post = new Post();
        return post;
    }
    @ModelAttribute("listPost")
    public Iterable<Post> listPost() {
        Iterable<Post> listPost = postService.getAllByOrderByDateDesc();
        return listPost;
    }

    @ModelAttribute("myListPost")
    public Iterable<Post> MylistPost() {
        Iterable<Post> listPost = postService.getAllUserOrderByDateDesc(userService.getCurrentUser());
        return listPost;
    }


    @PostMapping("/like/{id}")
    public String likePost(@ModelAttribute("likePost") LikePost like, @PathVariable Long id) {
        Post post = postService.findById(id).get();
        User user = userService.findByName(getUserCurrent().getName());
        like.setPost(post);
        like.setUser(user);
        likeService.save(like);
        return "redirect:/home/timeline/viewpost/" + id;
    }

    @ModelAttribute("likePost")
    public LikePost newLikePost() {
        LikePost likePost = new LikePost();
        return likePost;
    }

}
