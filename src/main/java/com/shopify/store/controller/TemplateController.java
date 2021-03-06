package com.shopify.store.controller;

import com.shopify.store.model.Image;
import com.shopify.store.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class TemplateController {

    private final ImageService imageService;

    @Autowired
    public TemplateController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("all_images")
    public String getImagesView(
            Model model,
            Principal principal
    ) {
        List<Image> allAvailableImages = imageService.getAllAvailableImages(principal);
        model.addAttribute("images", allAvailableImages);
        return "all_images";
    }

    @GetMapping("my_images")
    public String getMyImagesView(
            Model model,
            Principal principal
    ) {
        List<Image> myImages = imageService.getMyImages(principal);

        model.addAttribute("images", myImages);

        return "my_images";
    }

    @GetMapping("addImage")
    public String getAddImageView(
            Model model
    ) {
        model.addAttribute("image", new Image());
        return "add_image";
    }
}
