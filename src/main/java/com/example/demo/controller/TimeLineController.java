package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.categoryservice.CategoryService;
import com.example.demo.service.commentservice.CommentService;
import com.example.demo.service.postservice.PostService;
import com.example.demo.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/home/timeline")
public class TimeLineController {
    String mCloudName = "dnulbp9wi";
    String mApiKey = "388747591265657";
    String mApiSecret = "QrSQljoMltB5OgDmxQM81UBSB-0";

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    CommentService commentService;

    @GetMapping()
    public ModelAndView home(@ModelAttribute String username) {
        ModelAndView modelAndView = new ModelAndView("home");
//        Iterable<Post> postList = postService.findAll();
//        modelAndView.addObject("listPost", postList);
        return modelAndView;
    }

    @GetMapping("haslogin")
    public ModelAndView homehaslogin() {
        ModelAndView modelAndView = new ModelAndView("homehaslogin");
        return modelAndView;
    }

    @GetMapping("/viewpost/{id}")
    public ModelAndView viewPost(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        ModelAndView modelAndView = new ModelAndView("viewpost");
        modelAndView.addObject("post", post);
        return modelAndView;
    }


    @ModelAttribute("post")
    public Post newPost() {
        Post post = new Post();
        return post;
    }

    @ModelAttribute("categorys")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @ModelAttribute("listPost")
    public Iterable<Post> listPost() {
        Iterable<Post> listPost = postService.getAllByOrderByDateDesc();
        return listPost;
    }
    @ModelAttribute("myListPost")
    public Iterable<Post> MylistPost(){
        Iterable<Post> listPost = postService.getAllUserOrderByDateDesc(userService.getCurrentUser());
        return listPost;
    }
    @ModelAttribute("comment")
    public Comment newComment(){
        Comment comment = new Comment();
        return comment;
    }
    @ModelAttribute("user")
    public User currenUser(){
        return userService.getCurrentUser();
    }



    @GetMapping("/khongcoquyen")
    public String accessDenied(){
        return "error";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/trangchu")
    public String trangchu(){
        return "timeline";
    }
}
