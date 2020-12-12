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

import java.util.Iterator;
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
        return modelAndView;
    }

    //Tac gia: The Phen`
    @GetMapping("/category/{id}")
    public ModelAndView listCategory(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        Iterable<Post> postsByCategory = postService.findAllByCategory(category.get());
        ModelAndView modelAndView = new ModelAndView("category");
        modelAndView.addObject("category", category.get());
        modelAndView.addObject("posts", postsByCategory);
        return modelAndView;
    }

    @GetMapping("/viewpage/{id}")
    public ModelAndView viewpage(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("viewpage");
        User user = userService.findById(id).get();
        modelAndView.addObject("user",user);
        Iterable<Post> posts = postService.getAllUserOrderByDateDesc(user);
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("idUser", id);
        return modelAndView;

    }

    //Tac gia: The Phen
    @GetMapping("/viewpost/{id}")
    public ModelAndView viewPost(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        Iterable<Comment> comments = commentService.getAllByPost(post.get());
        Iterator<Comment> commentIterator = comments.iterator();
        ModelAndView modelAndView = new ModelAndView("viewpost");
        modelAndView.addObject("post", post.get());
        modelAndView.addObject("comments", commentIterator);
        return modelAndView;
    }

    @GetMapping("/haslogin")
    public ModelAndView homehaslogin() {
        ModelAndView modelAndView = new ModelAndView("homehaslogin");
        return modelAndView;
    }

    @ModelAttribute("post")
    public Post newPost() {
        Post post = new Post();
        return post;
    }

    @ModelAttribute("categories")
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
