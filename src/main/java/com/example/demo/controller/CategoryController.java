package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.categoryservice.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.Optional;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping()
    public ModelAndView listCategory(){
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("category/list");
        modelAndView.addObject("categories" , categories);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createCategory(){
        Category category = new Category();
        ModelAndView modelAndView = new ModelAndView("category/create");
        modelAndView.addObject("category", category);
        return modelAndView;
    }
    @PostMapping("/showCreate")
    public ModelAndView showCreate(Category category){
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("redirect:/categories");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView editCategory(@PathVariable Long id){
        Optional<Category> category = categoryService.findById(id);
        ModelAndView modelAndView = new ModelAndView("category/edit");
        modelAndView.addObject("category", category);
        return modelAndView;
    }
    @PostMapping("/edit/{id}")
    public ModelAndView showEdit(Category category){
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("redirect:/categories");
        modelAndView.addObject("category", category);
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView deleteCategory(@PathVariable Long id){
        Optional<Category> category = categoryService.findById(id);
        categoryService.remove(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/categories");
        return modelAndView;
    }

}