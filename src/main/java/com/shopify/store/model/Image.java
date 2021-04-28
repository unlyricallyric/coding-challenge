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
    private byte[] image_byte;

    public Image(UUID id, String originalName) {
        this.id = id;
        this.originalName = originalName;
    }
}
