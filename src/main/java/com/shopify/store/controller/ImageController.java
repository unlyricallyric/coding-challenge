package com.shopify.store.controller;

import com.shopify.store.dao.ImageDao;
import com.shopify.store.model.Image;
import com.shopify.store.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("image")
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
    public ResponseEntity<String> uploadImage(
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("original_image_name") String image_name
    ) {
        return imageService.saveImage(imageFile, image_name);
    }

    @GetMapping("test")
    public List<Image> getTest() {
        return imageService.getAllImages();
    }
}
