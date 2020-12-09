package com.example.demo.Repository;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String username);

    Iterable<User> getAllByRoleId(Long id);

    Iterable<User> getAllByRoleIsNotContaining(Long id);

    Iterable<User> getAllByRoleOrRole(Role role1, Role role2);

    Iterable<User> getAllByNameIsContaining(String name);
}
