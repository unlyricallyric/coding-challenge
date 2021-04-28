package com.shopify.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    private UUID id;
    private String originalName;
    private String hashing;
    private String username;
    private boolean isPublic;
    private byte[] image_byte;

    public Image(UUID id, String originalName, String hashing, String username, boolean isPublic) {
        this.id = id;
        this.originalName = originalName;
        this.hashing = hashing;
        this.username = username;
        this.isPublic = isPublic;
    }

    public Image(String originalName, String hashing, String username, boolean isPublic) {
        this.originalName = originalName;
        this.hashing = hashing;
        this.username = username;
        this.isPublic = isPublic;
    }
}
