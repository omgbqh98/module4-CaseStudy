package com.example.demo.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.model.Category;
import com.example.demo.service.categoryservice.CategoryService;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {

    String mCloudName = "dnulbp9wi";
    String mApiKey = "388747591265657";
    String mApiSecret = "QrSQljoMltB5OgDmxQM81UBSB-0";
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/admin/category")
    public ModelAndView listCategory() {
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("category/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreate() {
        ModelAndView modelAndView = new ModelAndView("category/create");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }


    @PostMapping("/create")
    public ModelAndView createCategory(@ModelAttribute("category") Category category, @ModelAttribute("iconCategoryFile") MultipartFile iconCategoryFile) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/category");
        Category category1 = categoryService.save(category);
        category1.setIconCategoryFile(iconCategoryFile);
        Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
        try {
            File avFile = Files.createTempFile("temp", iconCategoryFile.getOriginalFilename()).toFile();
            iconCategoryFile.transferTo(avFile);
            Map responseAV = c.uploader().upload(avFile, ObjectUtils.emptyMap());
            JSONObject jsonAV = new JSONObject(responseAV);
            String urlAV = jsonAV.getString("url");
            category1.setIcon(urlAV);
            categoryService.save(category1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEdit(@PathVariable Long id) {
        Category category = categoryService.findById(id).get();
        ModelAndView modelAndView = new ModelAndView("category/edit");
        modelAndView.addObject("category", category);
        return modelAndView;
    }


    @PostMapping("/edit/{id}")
    public ModelAndView editCategory(@ModelAttribute("category") Category category, @ModelAttribute("iconCategoryFile") MultipartFile iconCategoryFile) {
        Iterable<Category> categories = categoryService.findAll();
        Category category1 = categoryService.findById(category.getId_category()).get();
        category1.setIconCategoryFile(iconCategoryFile);
        category1.setName(category.getName());
        Cloudinary c = new Cloudinary("cloudinary://" + mApiKey + ":" + mApiSecret + "@" + mCloudName);
        try {
            File avFile = Files.createTempFile("temp", iconCategoryFile.getOriginalFilename()).toFile();
            iconCategoryFile.transferTo(avFile);
            Map responseAV = c.uploader().upload(avFile, ObjectUtils.emptyMap());
            JSONObject jsonAV = new JSONObject(responseAV);
            String urlAV = jsonAV.getString("url");
            category1.setIcon(urlAV);
            categoryService.save(category1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/category");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }
}