package com.example.demo.controller;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.roleservice.RoleService;
import com.example.demo.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @ModelAttribute("roles")
    public Iterable<Role> roles(Pageable pageable) {
        return roleService.findAll(pageable);
    }

    @ModelAttribute("roles")
    public Iterable<Role> roles() {
        return roleService.findAll();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/khongcoquyen")
    public String accessDenied() {
        return "redirect:/login";
    }

    @GetMapping("")
    public ModelAndView listUser(@RequestParam("s") Optional<String> s, @PageableDefault(size = 5) Pageable pageable) {
        Page<User> users;
        if (s.isPresent()) {
            users = userService.findAllByNameContaining(s.get(), pageable);
        } else {
            users = userService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("listUsers", users);
//        Role role_user = roleService.getById((long) 2);
//        Role role_guest = roleService.getById((long) 3);
//        modelAndView.addObject("listUsers", userService.getAllByRoleOrRole(role_user, role_guest));
        return modelAndView;
    }



//    @GetMapping("")
//    public ModelAndView adminPage(){
//        ModelAndView modelAndView = new ModelAndView("admin");
//        Role role_user = roleService.getById((long) 2);
//        Role role_guest = roleService.getById((long) 3);
//        modelAndView.addObject("listUsers",userService.getAllByRoleOrRole(role_user,role_guest));
//        return modelAndView;
//    }





    @GetMapping("/blockUser/{id}")
    public ModelAndView block(@PathVariable Long id,Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        Optional<User> currentUser = userService.findById(id);
        User user = currentUser.get();
        Role role_guest = roleService.getById((long) 3);
//        Role role_user = roleService.getById((long) 2);
        user.setRole(role_guest);
        user.setStatus("disable");
        userService.save(user);
        modelAndView.addObject("listUsers", userService.findAll(pageable));
        return modelAndView;
    }

    @GetMapping("/unBlockUser/{id}")
    public ModelAndView unBlock(@PathVariable Long id, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin");
        Optional<User> currentUser = userService.findById(id);
        User user = currentUser.get();
        Role role_user = roleService.getById((long) 2);
        user.setRole(role_user);
        user.setStatus("active");
        userService.save(user);
        modelAndView.addObject("listUsers", userService.findAll(pageable));
        return modelAndView;
    }

}
