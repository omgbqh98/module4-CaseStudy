package com.example.demo.model;

import javax.persistence.*;

@Entity
@Table
public class LikePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long like_id;



    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public LikePost() {
    }




    public LikePost(Long like_id, Post post, User user) {
        this.like_id = like_id;
        this.post = post;
        this.user = user;
    }

    public Long getLike_id() {
        return like_id;
    }

    public void setLike_id(Long like_id) {
        this.like_id = like_id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
