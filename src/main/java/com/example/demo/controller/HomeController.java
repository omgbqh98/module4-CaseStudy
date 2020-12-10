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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/block")
    public String block() {
        return "error";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/home/timeline";
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

    @PostMapping("/resetsuccess")
    public String newPassword(@Validated @ModelAttribute("user") User user, @RequestParam String password, RedirectAttributes redirect, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return "redirect:/newpassword";
        }
        user.setPass(password);
        userService.save(user);
        redirect.addFlashAttribute("message", "Cập nhật Mật khẩu thành công!");
        return "redirect:/login";
    }

    @GetMapping("/resetpassword")
    public ModelAndView resetPassword() {
        ModelAndView modelAndView = new ModelAndView("verification");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/newpassword")
    public ModelAndView resetPassword(@ModelAttribute("user") User user) {
        ModelAndView modelAndView = new ModelAndView("verification");
        for (User userFind : userService.findAll()) {
            if (user.getName().equals(userFind.getName())) {
                if (user.getQ1().equals(userFind.getQ1())) {
                    if (user.getQ2().equals(userFind.getQ2())) {
                        User userEdit = userService.getUserByUsername(user.getName());
                        ModelAndView modelAndViewNew = new ModelAndView("newpassword");
                        modelAndViewNew.addObject("message", "Input your new password!");
                        modelAndViewNew.addObject("user", userEdit);
                        return modelAndViewNew;
                    }
                }
            }
        }
        modelAndView.addObject("message", "Please try again!");
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
