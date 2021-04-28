package com.shopify.store.dao;

import com.shopify.store.model.Image;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository("imageDao")
public class ImageDataAccessService implements ImageDao {

    private static List<Image> DB = new ArrayList<>();

    @Override
    public int insertImage(UUID id, Image image) {
        DB.add(new Image(
                id,
                image.getOriginalName(),
                image.getHashing(),
                image.getUsername(),
                image.isPublic()
        ));
        return 1;
    }

    @Override
    public List<Image> getAllImages() {
        return DB;
    }

    @Override
    public List<Image> getMyImages(String user) {

        return DB.stream().filter(
                image -> image.getUsername().equals(user)
        ).collect(Collectors.toList());

    }

}
