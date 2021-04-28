package com.shopify.store.dao;

import com.shopify.store.model.Image;

import java.util.List;
import java.util.UUID;

public interface ImageDao {

    int insertImage(UUID id, Image image);

    default int insertImage(Image image) {
        UUID id = UUID.randomUUID();
        return insertImage(id, image);
    };

    List<Image> getAllImages();

    List<Image> getMyImages(String user);

}
