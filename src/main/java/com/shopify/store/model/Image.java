package com.shopify.store.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    private Long id;
    private String original_image_name;
    private String UUID_name;
    private String hashing;
    private String username;
    private byte[] image_byte;

}
