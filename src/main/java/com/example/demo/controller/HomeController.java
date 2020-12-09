package com.example.demo.controller;

import com.example.demo.Repository.UserRepository;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.roleservice.RoleService;
import com.example.demo.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }


    @PostMapping("/register")
    public ModelAndView register(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            ModelAndView modelAndView = new ModelAndView("register");
            return modelAndView;
        }
        // *Tác giả: Thế Phèn
        // Regíter check trùng Username và thông báo lỗi
        for (User userFind : userService.findAll()) {
            if (userFind.getName().equals(user.getName())) {
                ModelAndView modelAndView = new ModelAndView("register");
                modelAndView.addObject("message", "Tai khoan nay da duoc tao!");
                return modelAndView;
            }
        }
        ModelAndView modelAndView = new ModelAndView("login");
        Optional<Role> role = roleService.findById((long) 2);
        user.setRole(role.get());
        user.setStatus("active");
        user.setAvatar("https://img.thuthuatphanmem.vn/uploads/2018/09/19/avatar-facebook-chat-4_105604005.jpg");
        userService.save(user);
        modelAndView.addObject("user", user);
        return modelAndView;
    }


}
