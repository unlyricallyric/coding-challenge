package com.shopify.store.controller;

import com.shopify.store.model.Image;
import com.shopify.store.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("add")
    public void addImage(@RequestBody Image image) {
        imageService.addImage(image);
    }

    @PostMapping("upload")
    public String uploadImage(
            @RequestParam("imageFile") MultipartFile[] imageFile,
            @RequestParam("image_privacy") String image_privacy,
            RedirectAttributes redirectAttrs,
            Principal principal
    ) {
        boolean isPublic = (image_privacy.equals("true"))?true:false;
        List<String> response = imageService.saveImage(imageFile, principal.getName(), isPublic);
        redirectAttrs.addFlashAttribute("response", response);
        return "redirect:/addImage";
    }

    @DeleteMapping("delete/{id}")
    public String deleteImageById(@PathVariable("id") UUID id) {
        return id.toString();
    }

    @GetMapping("test")
    public List<Image> getTest() {
        return imageService.getAllImages();
    }
}
