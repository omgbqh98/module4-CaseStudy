package com.example.demo.controller;
import com.example.demo.model.*;
import com.example.demo.service.categoryservice.CategoryService;
import com.example.demo.service.commentservice.CommentService;
import com.example.demo.service.likeservice.LikeService;
import com.example.demo.service.postservice.PostService;
import com.example.demo.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Iterator;
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
    @Autowired
    LikeService likeService;

    @ModelAttribute("countPost")
    private Long countPost(){
        return postService.countPost();
    }

    @ModelAttribute("likePost")
    public LikePost newLikePost() {
        LikePost likePost = new LikePost();
        return likePost;
    }
    @ModelAttribute("user")
    public User getUserCurrent() {
        User userCurrent = userService.getCurrentUser();
        return userCurrent;
    }

//    @ModelAttribute("countLike")
//    public Long countLike() {
//        Long like = likeService.countAllLike();
//        return like;
//    }

    @GetMapping("/haslogin")
    public ModelAndView homehaslogin(@RequestParam("s") Optional<String> s , @ModelAttribute String username) {
        Iterable<Post> posts;
        if (s.isPresent()) {
            posts = postService.findByTitleContaining(s.get());
        } else {
            posts = postService.getAllByOrderByDateDesc();
        }
        ModelAndView modelAndView = new ModelAndView("homehaslogin");
        modelAndView.addObject("listPost", posts);
        return modelAndView;
    }

    @GetMapping()
    public ModelAndView home(@RequestParam("s") Optional<String> s , @ModelAttribute String username) {
        Iterable<Post> posts;
        if (s.isPresent()) {
            posts = postService.findByTitleContaining(s.get());
        } else {
            posts = postService.getAllByOrderByDateDesc();
        }
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("listPost", posts);
        return modelAndView;
    }

    @ModelAttribute("listPost")
    public Iterable<Post> listPost1() {
        Iterable<Post> listPost = postService.getAllByOrderByDateDesc();
        return listPost;
    }

    //Tac gia: The Phen`
    @GetMapping("/category/{id}")
    public ModelAndView listCategory(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);
        Iterable<Post> postsByCategory = postService.findAllByCategory(category.get());
        if (getUserCurrent() != null) {
            ModelAndView modelAndView = new ModelAndView("category/categoryFindHasLogin");
            modelAndView.addObject("category", category.get());
            modelAndView.addObject("posts", postsByCategory);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("category/categoryFind");
            modelAndView.addObject("category", category.get());
            modelAndView.addObject("posts", postsByCategory);
            return modelAndView;
        }

    }

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @PostMapping("/AdminDeletePost")
    public ModelAndView deletePost(@ModelAttribute("post") Post post, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "delete success");
        ModelAndView modelAndView = new ModelAndView("redirect:/listPostAdmin");
//        User user = userService.findById(id).get();
        postService.remove(post.getPost_id());
        return modelAndView;
    }

    @GetMapping("/adminDeletePost/{id}")
    public ModelAndView deletePost(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("post/admindeletepost");
        Optional<Post> post = postService.findById(id);
        modelAndView.addObject("posts", post.get());
        return modelAndView;
    }

    @GetMapping("/viewpageAdmin/{id}")
    public ModelAndView viewpageAdmin(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("adminviewpage");
        User user = userService.findById(id).get();
        modelAndView.addObject("user",user);
        Iterable<Post> posts = postService.getAllUserOrderByDateDesc(user);
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("idUser", id);
        return modelAndView;
    }

    @GetMapping("/viewpagehaslogin/{id}")
    public ModelAndView viewpageHasLogin(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("viewpagehaslogin");
        User user = userService.findById(id).get();
        modelAndView.addObject("user",user);
        Iterable<Post> posts = postService.getAllUserOrderByDateDesc(user);
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("idUser", id);
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

    @GetMapping("viewpost-myhome/{id}")
    public ModelAndView viewpostmyhome(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        Iterable<Comment> comments = commentService.getAllByPost(post.get());
        Iterator<Comment> commentIterator = comments.iterator();
        ModelAndView modelAndView = new ModelAndView("viewpostmyhome");
        modelAndView.addObject("post", post.get());
        modelAndView.addObject("comments", commentIterator);
        return modelAndView;
    }

    @GetMapping("viewpost-admin/{id}")
    public ModelAndView viewpostAdmin(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        Iterable<Comment> comments = commentService.getAllByPost(post.get());
        Iterator<Comment> commentIterator = comments.iterator();
        ModelAndView modelAndView = new ModelAndView("post/viewpostadmin");
        modelAndView.addObject("post", post.get());
        modelAndView.addObject("comments", commentIterator);
        return modelAndView;
    }

    @GetMapping("/viewposthaslogin/{id}")
    public ModelAndView viewPostHasLogin(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        Iterable<Comment> comments = commentService.getAllByPost(post.get());
        Iterator<Comment> commentIterator = comments.iterator();
        Long countComment = commentService.countByPostId(post.get());
        Long countLike = likeService.countLike(post.get());
        ModelAndView modelAndView = new ModelAndView("viewposthaslogin");
        modelAndView.addObject("countComment", countComment);
        modelAndView.addObject("post", post.get());
        modelAndView.addObject("comments", commentIterator);
        modelAndView.addObject("countLike",countLike);
        return modelAndView;
    }


    //Tac gia: The Phen
    @GetMapping("/viewpost/{id}")
    public ModelAndView viewPost(@PathVariable Long id) {
        Optional<Post> post = postService.findById(id);
        Iterable<Comment> comments = commentService.getAllByPost(post.get());
        Iterator<Comment> commentIterator = comments.iterator();
        Long countComment = commentService.countByPostId(post.get());
        Long countLike = likeService.countLike(post.get());
        ModelAndView modelAndView = new ModelAndView("viewpost");
        modelAndView.addObject("countComment", countComment);
        modelAndView.addObject("post", post.get());
        modelAndView.addObject("comments", commentIterator);
        modelAndView.addObject("countLike",countLike);
        return modelAndView;
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


}
