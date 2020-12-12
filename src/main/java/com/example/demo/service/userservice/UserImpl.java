package com.example.demo.service.userservice;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;


//    @Override
//    public User finById(Long id) {
//        return userRepository.findById(id);
//    }

    @Override
    public Iterable<User> findAllByRole(Role role) {
        return userRepository.findAllByRole(role);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findAllByNameContaining(String name, Pageable pageable) {
        return userRepository.findAllByNameContaining(name,pageable);
    }

    @Override
    public User findByName(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public Iterable<User> getAllByRoleId(Long id) {
        return userRepository.getAllByRoleId(id);
    }

    @Override
    public User getCurrentUser() {
        User user;
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        user = this.findByName(userName);
        return user;
    }

    @Override
    public Iterable<User> getAllByRoleIsNotContaining(Long id) {
        return userRepository.getAllByRoleIsNotContaining(id);
    }

    @Override
    public Iterable<User> getAllByNameIsContaining(String name) {
        return userRepository.getAllByNameIsContaining(name);
    }



    @Override
    public Iterable<User> getAllByRoleOrRole(Role role1, Role role2) {
        return userRepository.getAllByRoleOrRole(role1,role2);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
    userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findByName(username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(user.getRole());

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPass(),
                authorities);
        return userDetails;
    }


}
