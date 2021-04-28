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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class ImageService {

    private final ImageDao imageDao;
    private final String storage = "store/src/main/resources/static/img/";

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

    public ResponseEntity<String> saveImage(MultipartFile imageFile, String image_name, String username) {
        try {

            Image img = new Image(
                    image_name,
                    getMD5HashFromByte(imageFile.getBytes()),
                    username
            );

            imageDao.insertImage(img);

            FileOutputStream fos = new FileOutputStream(storage + image_name);

            fos.write(imageFile.getBytes());
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("issue getting bytes array");
        }

        return ResponseEntity.ok("image successfully uploaded!");
    }

    public String getMD5HashFromByte(byte[] image_bytes) throws NoSuchAlgorithmException, IOException {
        //convert image bytes to hash bytes
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(image_bytes);
        byte[] hash = md.digest();

        //convert has bytes to hash string
        String hexString = "";
        for (int i=0; i < hash.length; i++) { //for loop ID:1
            hexString +=
                    Integer.toString( ( hash[i] & 0xff ) + 0x100, 16).substring( 1 );
        }

        return hexString;

    }
}