package com.shopify.store.service;

import com.shopify.store.dao.ImageDao;
import com.shopify.store.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    private final ImageDao imageDao;

    @Autowired
    public ImageService(@Qualifier("imageDao") ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public int addImage(Image image) {
        return imageDao.insertImage(image);
    }

    public List<Image> getAllImages() {
        return imageDao.getAllImages();
    }

    public ResponseEntity<String> saveImage(MultipartFile imageFile, String image_name) {
        try {
            String originalFilename = imageFile.getOriginalFilename();

            FileOutputStream fos = new FileOutputStream("store/src/main/resources/static/img/test.png");

            fos.write(imageFile.getBytes());

            Image img = new Image();
            img.setOriginalName(image_name);
            //Image img = new Image();

            //img.setImage_byte(imageFile.getBytes());

            //imageRepository.save(img);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("issue getting bytes array");
        }

        return ResponseEntity.ok("image successfully uploaded!");
    }
}
