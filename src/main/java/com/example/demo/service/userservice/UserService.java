package com.example.demo.service.userservice;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.GeneralService;

public interface UserService extends GeneralService<User> {
    User getUserByUsername(String username);

    Iterable<User> getAllByRoleId(Long id);

    User getCurrentUser();

    Iterable<User> getAllByRoleIsNotContaining(Long id);

    Iterable<User> getAllByNameIsContaining(String name);

    Iterable<User> getAllByRoleOrRole(Role role1, Role role2);
}
