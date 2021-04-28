package com.shopify.store.dao;

import com.shopify.store.model.Image;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("imageDao")
public class ImageDataAccessService implements ImageDao {

    private static List<Image> DB = new ArrayList<>();

    @Override
    public int insertImage(UUID id, Image image) {
        DB.add(new Image(
                id,
                image.getOriginalName(),
                image.getHashing(),
                image.getUsername()
        ));
        return 1;
    }

    @Override
    public List<Image> getAllImages() {
        return DB;
    }

}
