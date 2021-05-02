package com.shopify.store.service;

import com.shopify.store.dao.ImageDao;
import com.shopify.store.dao.ImageDataAccessService;
import com.shopify.store.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private final ImageDao imageDao;
    private final String storage = "store/target/classes/static/img/";

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

    public List<String> saveImage(MultipartFile[] imageFile, String username, boolean isPublic) {
        //list which saves error/success messages if there is any
        List<String> response = new ArrayList<>();

        try {
            for(int i=0; i<imageFile.length; i++) {

                if (!imageFile[i].isEmpty()) {
                    String originalName = imageFile[i].getOriginalFilename();
                    String md5HashFromByte = getMD5HashFromByte(imageFile[i].getBytes());
                    String ext = originalName.split("\\.")[originalName.split("\\.").length-1];

                    if(!ImageDataAccessService.allowed_ext.contains(ext)){
                        response.add(String.format("The image %s has an extension of %s that is not allowed," +
                                " please choose another image!", originalName, ext));
                        continue;
                    }

                    String hashName = String.format("%s.%s", md5HashFromByte, ext);

                    Image img = new Image(
                            originalName.split("\\.")[0],
                            md5HashFromByte,
                            username,
                            isPublic,
                            hashName
                    );

                    /*
                        Check if the same exact file already exist using md5 hashing if there is
                        already a copy, will use reference instead of saving to host
                     */
                    if(!isDuplicated(img.getHashName())) {
                        FileOutputStream fos = new FileOutputStream(storage + hashName);
                        fos.write(imageFile[i].getBytes());
                        fos.close();
                    }

                    imageDao.insertImage(img);

                } else {
                    response.add(String.format("The %sth image was not uploaded due to its empty, please re-upload it!", i+1));
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            response.add("issue getting bytes array");
            return response;
        }

        //return successful message if there is no error message
        if(response.isEmpty()) {
            response.add("image successfully uploaded!");
        }

        return response;
    }

    public String getMD5HashFromByte(byte[] image_bytes) throws NoSuchAlgorithmException, IOException {
        //convert image bytes to hash bytes
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(image_bytes);
        byte[] hash = md.digest();

        //convert has bytes to hash string
        String hexString = "";
        for (int i=0; i < hash.length; i++) {
            hexString += Integer.toString( ( hash[i] & 0xff ) + 0x100, 16).substring( 1 );
        }

        return hexString;

    }

    public List<Image> getMyImages(Principal principal) {
        String user = principal.getName();

        return imageDao.getMyImages(user);
    }

    public List<Image> getAllAvailableImages(Principal principal) {
        String user = principal.getName();

        return imageDao.getAllAvailableImages(user);
    }

    public String deleteImageByUsernameAndImageId(String username, String hashName) {
        try {
            /*
                Check if the file to be deleted has more than one reference
                delete file if no multiple references are found pointing
                to the same file.
             */

            if(!isReference(hashName)){
                Files.deleteIfExists(Paths.get(storage + hashName));
            }

            imageDao.deleteImageByUsernameAndImageId(username, hashName);
            return "Image successfully deleted!";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Having issue deleting image, please try again!";
    }

    public boolean isDuplicated(String hashName) {
        return imageDao.isDuplicated(hashName);
    }

    public boolean isReference(String hashName) {
        return imageDao.isReference(hashName);
    }
}
