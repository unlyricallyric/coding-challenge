package com.shopify.store.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shopify.store.model.Image;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ImageDataAccessService.class})
@ExtendWith(SpringExtension.class)
public class ImageDataAccessServiceTest {
    @Autowired
    private ImageDataAccessService imageDataAccessService;

    @Test
    public void testInsertImage() {
        UUID id = UUID.randomUUID();
        assertEquals(1, this.imageDataAccessService.insertImage(id,
                new Image("originalName", "hashing", "username", true, "hashName")));
    }

    @Test
    public void testInsertImage2() {
        UUID id = UUID.randomUUID();
        assertEquals(1, this.imageDataAccessService.insertImage(id,
                new Image("originalName", "hashing", "username", false, "hashName")));
    }

    @Test
    public void testInsertImage3() {
        UUID id = UUID.randomUUID();
        Image image = mock(Image.class);
        when(image.getHashName()).thenReturn("hashName");
        when(image.isPublic()).thenReturn(true);
        when(image.getUsername()).thenReturn("username");
        when(image.getHashing()).thenReturn("hashing");
        when(image.getOriginalName()).thenReturn("originalName");
        assertEquals(1, this.imageDataAccessService.insertImage(id, image));
        verify(image).getHashName();
        verify(image).isPublic();
        verify(image).getOriginalName();
        verify(image).getHashing();
        verify(image).getUsername();
    }

    @Test
    public void testGetMyImages() {
        assertTrue(this.imageDataAccessService.getMyImages("TEST_STRING").isEmpty());
    }

    @Test
    public void testGetAllAvailableImages() {
        assertTrue(this.imageDataAccessService.getAllAvailableImages("TEST_STRING").isEmpty());
    }
}

