package com.example.demo.Repository;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Iterable<User> findAllByRole(Role role);

    User findByName(String username);

    Page<User> findAllByNameContaining(String name, Pageable pageable);
    Iterable<User> getAllByRoleId(Long id);

    Iterable<User> getAllByRoleIsNotContaining(Long id);

    Iterable<User> getAllByRoleOrRole(Role role1, Role role2);

    Iterable<User> getAllByNameIsContaining(String name);
}
