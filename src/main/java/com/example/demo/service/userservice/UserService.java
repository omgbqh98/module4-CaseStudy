package com.example.demo.service.userservice;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends GeneralService<User> {
//    User finById(Long id);

    Iterable<User> findAllByRole(Role role);

    Page<User> findAll(Pageable pageable);

    Page<User> findAllByNameContaining(String name, Pageable pageable);

    User findByName(String username);

    Iterable<User> getAllByRoleId(Long id);

    User getCurrentUser();

    Iterable<User> getAllByRoleIsNotContaining(Long id);

    Iterable<User> getAllByNameIsContaining(String name);

    Iterable<User> getAllByRoleOrRole(Role role1, Role role2);
}
