package com.example.demo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.categoryservice.CategoryService;
import com.example.demo.service.commentservice.CommentService;
import com.example.demo.service.postservice.PostService;
import com.example.demo.service.userservice.UserService;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    CommentService commentService;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    String mCloudName = "dnulbp9wi";
    String mApiKey = "388747591265657";
    String mApiSecret = "QrSQljoMltB5OgDmxQM81UBSB-0";

    @GetMapping("/delete-comment/{commentId}/{postId}")
    public ModelAndView deleteComment(@PathVariable Long commentId, @PathVariable Long postId) {
        Optional<Comment> comment = commentService.findById(commentId);
        Optional<Post> post = postService.findById(postId);
        ModelAndView modelAndView = new ModelAndView("comment/delete");
        modelAndView.addObject("post", post.get());
        modelAndView.addObject("comment", comment.get());
        return modelAndView;
    }

    @PostMapping("/delete-comment/{postId}")
    public ModelAndView deleteComment(@PathVariable Long postId, Comment comment) {
        Optional<Post> post = postService.findById(postId);
        commentService.remove(comment.getComment_id());
        ModelAndView modelAndView = new ModelAndView("redirect:/home/timeline/viewpost-myhome/" + post.get().getPost_id());
        return modelAndView;
    }

    @PostMapping("/create-comment/{id}")
    public String createComment(@ModelAttribute("comment") Comment comment, @PathVariable Long id) {
        Post post = postService.findById(id).get();
        User user = userService.findByName(getUserCurrent().getName());
        comment.setDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);
        commentService.save(comment);
        return "redirect:/home/timeline/viewpost/" + id;
    }


    @ModelAttribute("comment")
    public Comment newComment() {
        Comment comment = new Comment();
        return comment;
    }

    @GetMapping("/delete-post/{id}")
    public ModelAndView deletePost(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("post/deletepost");
        Optional<Post> post = postService.findById(id);
        modelAndView.addObject("posts", post.get());
        return modelAndView;
    }


    @PostMapping("/delete-post")
    public ModelAndView deletePost(@ModelAttribute("post") Post post, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "delete sucess");
        ModelAndView modelAndView = new ModelAndView("redirect:/user");
        postService.remove(post.getPost_id());
        return modelAndView;
    }


    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }


    @GetMapping()
    public ModelAndView home(@ModelAttribute String username) {
        ModelAndView modelAndView = new ModelAndView("myhome");
        return modelAndView;
    }


    @PostMapping("/createpost")
    public ModelAndView homePost(@ModelAttribute("post") Post post, @ModelAttribute("postImageFile") MultipartFile postImageFile) {
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
        modelAndView.addObject("post", post1);
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
        modelAndView.addObject("user", user);
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
    public String login() {
        return "login";
    }

    @ModelAttribute("userCurrent")
    public User getUserCurrent() {
        User userCurrent = userService.getCurrentUser();
        return userCurrent;
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

    @ModelAttribute("post")
    public Post newPost() {
        Post post = new Post();
        return post;
    }

}
