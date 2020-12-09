package com.example.demo.service.roleservice;

import com.example.demo.model.Role;
import com.example.demo.service.GeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService extends GeneralService<Role> {
    Role getById(Long id);
    Page<Role> findAll(Pageable pageable);
}
