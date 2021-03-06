package com.shopify.store.dao;

import com.shopify.store.model.Image;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository("imageDao")
public class ImageDataAccessService implements ImageDao {

    //Fake database
    private static List<Image> DB = new ArrayList<>();

    public final static List<String> allowed_ext = new ArrayList<>(
            Arrays.asList("jpg", "png", "gif", "webp", "tiff", "psd", "raw", "jpeg", "svg")
    );

    @Override
    public int insertImage(UUID id, Image image) {
        DB.add(new Image(
                id,
                image.getOriginalName(),
                image.getHashing(),
                image.getUsername(),
                image.isPublic(),
                image.getHashName()
        ));
        return 1;
    }

    @Override
    public List<Image> getAllImages() {
        return DB;
    }

    @Override
    public List<Image> getMyImages(String user) {
        //return all images that only belongs to the user
        return DB.stream().filter(
                image -> image.getUsername().equals(user)
        ).collect(Collectors.toList());

    }

    @Override
    public List<Image> getAllAvailableImages(String user) {
        //return all images that are public or belongs to user
        return DB.stream().filter(
                image -> image.getUsername().equals(user) || image.isPublic()
        ).collect(Collectors.toList());

    }

    @Override
    public void deleteImageByUsernameAndImageId(String username, String hashName) {
        List<Image> imageStream = DB.stream().filter(
                image -> image.getUsername().equals(username) && image.getHashName().equals(hashName)
        ).collect(Collectors.toList());

        DB.remove(imageStream.get(0));
    }

    //check if the same exact file exist already by comparing md5 hashing
    @Override
    public boolean isDuplicated(String hashName) {
        List<Image> imageStream = DB.stream().filter(
                image -> image.getHashName().equals(hashName)
        ).collect(Collectors.toList());

        return !imageStream.isEmpty();
    }

    //check an existing file has multiple reference
    @Override
    public boolean isReference(String hashName) {
        List<Image> imageStream = DB.stream().filter(
                image -> image.getHashName().equals(hashName)
        ).collect(Collectors.toList());

        return imageStream.size() > 1;
    }

}
