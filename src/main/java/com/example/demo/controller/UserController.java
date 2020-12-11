package com.example.demo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.categoryservice.CategoryService;
import com.example.demo.service.postservice.PostService;
import com.example.demo.service.userservice.UserService;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    String mCloudName = "dnulbp9wi";
    String mApiKey = "388747591265657";
    String mApiSecret = "QrSQljoMltB5OgDmxQM81UBSB-0";

    @GetMapping("/delete-post/{id}")
    private ModelAndView deletePost(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("deletepost");
       Optional<Post> post= postService.findById(id);
        modelAndView.addObject("posts", post.get());
        return modelAndView;
    }


    @ModelAttribute("categorys")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }
    @GetMapping()
    public ModelAndView home(@ModelAttribute String username){
        ModelAndView modelAndView = new ModelAndView("myhome");
        return modelAndView;
    }

    @PostMapping("/createpost")
    public ModelAndView homePost(@ModelAttribute("post") Post post, @ModelAttribute("postImageFile") MultipartFile postImageFile){
        ModelAndView modelAndView = new ModelAndView("redirect:/user");
        post.setUser(userService.getCurrentUser());
        post.setDate(LocalDateTime.now());
        Post post1 = postService.save(post);
        post1.setPostImageFile(postImageFile);
        Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
        try {
            File avFile = Files.createTempFile("temp", postImageFile.getOriginalFilename()).toFile();
            postImageFile.transferTo(avFile);
            Map responseAV = c.uploader().upload(avFile, ObjectUtils.emptyMap());
            JSONObject jsonAV = new JSONObject(responseAV);
            String urlAV = jsonAV.getString("url");
            post1.setPostImage(urlAV);
            postService.save(post1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("post",post1);
        return modelAndView;
    }



    @PostMapping("/avatar")
    public ModelAndView uploadAvatar(@ModelAttribute("avatar") MultipartFile avatar) {
        ModelAndView modelAndView = new ModelAndView("redirect:/user");
        User user = userService.getCurrentUser();
        user.setAvatarFile(avatar);
        Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
        try {
            File avFile = Files.createTempFile("temp", avatar.getOriginalFilename()).toFile();
            avatar.transferTo(avFile);
            Map responseAV = c.uploader().upload(avFile, ObjectUtils.emptyMap());
            JSONObject jsonAV = new JSONObject(responseAV);
            String urlAV = jsonAV.getString("url");
            user.setAvatar(urlAV);
            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        modelAndView.addObject("user",user);
        return modelAndView;
    }


    @GetMapping("/myhome")
    public ModelAndView myHome() {
        ModelAndView modelAndView = new ModelAndView("myhome");
        return modelAndView;
    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @ModelAttribute("user")
    public User currenUser(){
        return userService.getCurrentUser();
    }

    @ModelAttribute("listPost")
    public Iterable<Post> listPost(){
        Iterable<Post> listPost= postService.getAllByOrderByDateDesc();
        return listPost;
    }

    @ModelAttribute("myListPost")
    public Iterable<Post> MylistPost(){
        Iterable<Post> listPost = postService.getAllUserOrderByDateDesc(userService.getCurrentUser());
        return listPost;
    }
    @ModelAttribute("post")
    public Post newPost(){
        Post post = new Post();
        return post;
    }

}
