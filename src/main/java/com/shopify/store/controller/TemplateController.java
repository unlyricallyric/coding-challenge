package com.shopify.store.controller;

import com.shopify.store.model.Image;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("images")
    public String getImagesView() {
        return "images";
    }

    @GetMapping("addImage")
    public String getAddImageView(
            Model model
    ) {
        model.addAttribute("image", new Image());
        return "add_image";
    }
}
