package com.example.demo.service.roleservice;

import com.example.demo.model.Role;
import com.example.demo.service.GeneralService;

public interface RoleService extends GeneralService<Role> {
    Role getById(Long id);
}
