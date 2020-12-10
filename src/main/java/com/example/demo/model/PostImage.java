package com.example.demo.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Table
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postImage_id;
    private String img;
    @Transient
    private MultipartFile image;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostImage() {
    }

    public PostImage(String img, MultipartFile image, Post post) {
        this.img = img;
        this.image = image;
        this.post = post;
    }

    public Long getPostImage_id() {
        return postImage_id;
    }

    public void setPostImage_id(Long postImage_id) {
        this.postImage_id = postImage_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
