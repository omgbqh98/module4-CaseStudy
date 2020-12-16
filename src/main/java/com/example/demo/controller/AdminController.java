package com.example.demo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.*;
import com.example.demo.service.categoryservice.CategoryService;
import com.example.demo.service.commentservice.CommentService;
import com.example.demo.service.postservice.PostService;
import com.example.demo.service.roleservice.RoleService;
import com.example.demo.service.userservice.UserService;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    CategoryService categoryService;

    @ModelAttribute("roles")
    public Iterable<Role> roles(Pageable pageable) {
        return roleService.findAll(pageable);
    }

    @ModelAttribute("countUser")
    public Long countUser() {
        return userService.countByUser();
    }

    @ModelAttribute("roles")
    public Iterable<Role> roles() {
        return roleService.findAll();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/khongcoquyen")
    public String accessDenied() {
        return "error";
    }

    @GetMapping("")
    public ModelAndView listUser(@RequestParam("s") Optional<String> s, @PageableDefault(size = 5) Pageable pageable) {
        Page<User> users;
        if (s.isPresent()) {
            users = userService.findAllByNameContaining(s.get(), pageable);
        } else {
            users = userService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("listUsers", users);
        return modelAndView;
    }

    @GetMapping("/category")
    public ModelAndView categoryAdmin() {
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/add-roleAdmin/{id}")
    public ModelAndView addRoleAdmin(@PathVariable Long id, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        Optional<User> currentUser = userService.findById(id);
        User user = currentUser.get();
        Role role_guest = roleService.getById((long) 1);
        user.setRole(role_guest);
        user.setStatus("active");
        userService.save(user);
        modelAndView.addObject("listUsers", userService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/add-roleUser/{id}")
    public ModelAndView addRoleUser(@PathVariable Long id, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        Optional<User> currentUser = userService.findById(id);
        User user = currentUser.get();
        Role role_guest = roleService.getById((long) 2);
        user.setRole(role_guest);
        user.setStatus("active");
        userService.save(user);
        modelAndView.addObject("listUsers", userService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/viewpageadmin/{id}")
    public ModelAndView viewpageAdmin(@PathVariable Long id, @RequestParam("search") Optional<String> search, @PageableDefault(size = 5) Pageable pageable) {
        Page<Post> posts;
        if (search.isPresent()) {
            posts = postService.findAllPostByTitleAndUserId(search.get(), pageable, id);
        } else {
            posts = postService.findAllPostPageableWithUserId(pageable, id);
        }
        ModelAndView modelAndView = new ModelAndView("post/viewpageadmin");
        User idUser = userService.findById(id).get();
        Long countPost = postService.countPostByUserId(id);
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("idUser", idUser);
        modelAndView.addObject("countPost", countPost);
        return modelAndView;
    }

    @GetMapping("/delete-commentAdmin/{commentId}/{postId}")
    public ModelAndView deleteCommentAdmin(@PathVariable Long commentId, @PathVariable Long postId) {
        Optional<Comment> comment = commentService.findById(commentId);
        Optional<Post> post = postService.findById(postId);
        ModelAndView modelAndView = new ModelAndView("comment/deleteCommentAdmin");
        modelAndView.addObject("post", post.get());
        modelAndView.addObject("comment", comment.get());
        return modelAndView;
    }

    @PostMapping("/delete-commentAdmin/{id}")
    public ModelAndView deleteCommentAdmin(@PathVariable Long id, Comment comment) {
        commentService.remove(comment.getComment_id());
        Optional<Post> post = postService.findById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewpost-admin/" + post.get().getPost_id());
        return modelAndView;
    }

    @GetMapping("/viewpost-admin/{id}")
    public ModelAndView viewpostAdmin(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        Optional<User> user = userService.findById(post.get().getUser().getId());
        Iterable<Comment> comments = commentService.getAllByPost(post.get());
        Iterator<Comment> commentIterator = comments.iterator();
        ModelAndView modelAndView = new ModelAndView("post/viewpostadmin");
        modelAndView.addObject("post", post.get());
        modelAndView.addObject("comments", commentIterator);
        modelAndView.addObject("user", user.get());
        return modelAndView;
    }

    //    @PostMapping("/AdminDeletePost")
//    public ModelAndView deletePost(@ModelAttribute("post") Post post, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("success", "delete success");
//        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewpageadmin" + post.getUser().getId());
////        User user = userService.findById(id).get();
//        postService.remove(post.getPost_id());
//        return modelAndView;
//    }

    @PostMapping("/deletepost")
    public String deletePost(@ModelAttribute("post") Post post, RedirectAttributes redirect, Long id) {
//        Optional<User> userFind = userService.findById(id);
        postService.delete(post);
        redirect.addFlashAttribute("message", "Delete Post Success!");
//        ModelAndView modelAndView = new ModelAndView("redirect:/admin/viewpageadmin/" + userFind.get().getId());
//        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        return "redirect:/admin";
    }

    @GetMapping("/blockUser/{id}")
    public ModelAndView block(@PathVariable Long id, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        Optional<User> currentUser = userService.findById(id);
        User user = currentUser.get();
        Role role_guest = roleService.getById((long) 3);
        user.setRole(role_guest);
        user.setStatus("disable");
        userService.save(user);
        modelAndView.addObject("listUsers", userService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/unBlockUser/{id}")
    public ModelAndView unBlock(@PathVariable Long id, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        Optional<User> currentUser = userService.findById(id);
        User user = currentUser.get();
        Role role_user = roleService.getById((long) 2);
        user.setRole(role_user);
        user.setStatus("active");
        userService.save(user);
        modelAndView.addObject("listUsers", userService.findAll(pageable));
        return modelAndView;
    }

}
