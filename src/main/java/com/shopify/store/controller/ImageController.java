package com.shopify.store.controller;

import com.shopify.store.model.Image;
import com.shopify.store.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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
    ) throws IOException {
        boolean isPublic = image_privacy.equals("true");
        List<String> response = imageService.saveImage(imageFile, principal.getName(), isPublic);
        redirectAttrs.addFlashAttribute("response", response);
        return "redirect:/addImage";
    }

    @RequestMapping(value = "delete/{hashName}", method = RequestMethod.DELETE)
    public String deleteImageById(
            Principal principal,
            RedirectAttributes redirectAttrs,
            @PathVariable("hashName") String hashName
    ) {
        String message = imageService.deleteImageByUsernameAndImageId(principal.getName(), hashName);
        redirectAttrs.addFlashAttribute("message", message);
        return "redirect:/my_images";
    }

    @GetMapping("test")
    public List<Image> getTest() {
        return imageService.getAllImages();
    }
}
