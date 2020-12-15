package com.example.demo.service.postservice;
import com.example.demo.Repository.PostRepository;
import com.example.demo.model.Category;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;
    @Override
    public Iterable<Post> getAllPostByUserId(Long userId) {
        return postRepository.getAllByUserId(userId);
    }
    @Override
    public Iterable<Post> findAllByCategory(Category category) {
        return postRepository.findAllByCategory(category);
    }
    @Override
    public Iterable<Post> getAllByOrderByDateDesc() {
        return postRepository.getAllByOrderByDateDesc();
    }
    @Override
    public Iterable<Post> getAllUserOrderByDateDesc(User user) {
        return postRepository.getAllByUserOrderByDateDesc(user);
    }
    @Override
    public Page<Post> findAllPostByTitleAndUserId(String title, Pageable pageable, Long userId) {
        return postRepository.findAllByTitleContaining(title, pageable, userId);
    }
    @Override
    public Page<Post> findAllPostPageableWithUserId(Pageable pageable, Long userId) {
        return postRepository.findAllByUserId(pageable, userId);
    }
    @Override
    public Post getByPost_id(Long id) {
        return null;
    }
    //    @Override
//    public Post findByPost_Id(Long id) {
//        return postRepository.findByPost_id(id);
//    }
    @Override
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }
    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
    //    @Override
//    public Optional<Post> findById(Long id) {
//        return postRepository.findById(id);
//    }
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }
    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);
    }
    public void delete(Post post) {
        postRepository.delete(post);
    }
    @Override
    public Long countPost() {
        return postRepository.count();
    }
    @Override
    public Long countPostByUserId(Long id) {
        return postRepository.countAllByUserId(id);
    }
    @Override
    public Iterable<Post> findByTitleContaining(String title) {
        return postRepository.findByTitleContaining( title);
    }
}