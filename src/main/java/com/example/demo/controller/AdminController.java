package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.service.roleservice.RoleService;
import com.example.demo.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @ModelAttribute("roles")
    public Iterable<Role> roles() {
        return roleService.findAll();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/khongcoquyen")
    public String accessDenied(){
        return "khongcoquyen";
    }

    @GetMapping("")
    public ModelAndView adminPage() {
        ModelAndView modelAndView = new ModelAndView("admin");
        Role role_user = roleService.getById((long) 2);
        Role role_guest = roleService.getById((long) 3);
        modelAndView.addObject("listUsers", userService.getAllByRoleOrRole(role_user, role_guest));
        return modelAndView;
    }
}
