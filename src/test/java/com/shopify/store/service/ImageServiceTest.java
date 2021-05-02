package com.shopify.store.service;

import com.shopify.store.dao.ImageDao;
import com.shopify.store.model.Image;

import org.apache.catalina.connector.CoyotePrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ImageService.class})
@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private ImageDao imageDao;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        imageService = new ImageService(imageDao);
    }

    @Test
    void testGetAllImages() {
        // when
        imageService.getAllImages();

        // then
        verify(imageDao).getAllImages();
    }

    @Test
    void testSaveImage() {
        // given
        Image img = new Image(
                "37a245bc-2911-4883-b907-e8a5503b73c1",
                "ee10f82be4a74f1190a18090b2531f27",
                "johnson",
                true,
                "ee10f82be4a74f1190a18090b2531f27.png"
        );

        // when
        imageService.addImage(img);

        // then
        ArgumentCaptor<Image> imageArgumentCaptor = ArgumentCaptor.forClass(Image.class);

        verify(imageDao).insertImage(imageArgumentCaptor.capture());

        Image capturedImage = imageArgumentCaptor.getValue();

        assertThat(capturedImage).isEqualTo(img);
    }

    @Test
    void testGetMD5HashFromByte() throws NoSuchAlgorithmException, IOException {
        String testBytes = imageService.getMD5HashFromByte("test".getBytes());
        assertThat(testBytes).isEqualTo("098f6bcd4621d373cade4e832627b4f6");
    }

    @Test
    public void testGetMyImages() {
        // when
        imageService.getMyImages(new CoyotePrincipal("johnson"));

        // then
        verify(imageDao).getMyImages("johnson");
    }

    @Test
    void testGetAllAvailableImages() {
        // when
        imageService.getAllAvailableImages(new CoyotePrincipal("johnson"));

        // then
        verify(imageDao).getAllAvailableImages("johnson");
    }

    @Test
    void testDeleteImageByUsernameAndImageId() {
        // when
        imageService.deleteImageByUsernameAndImageId("johnson", "81c0fba9-f270-4fa2-88b8-6ebdad1699ea");

        // then
        verify(imageDao).deleteImageByUsernameAndImageId("johnson", "81c0fba9-f270-4fa2-88b8-6ebdad1699ea");
    }

    @Test
    public void testIsDuplicated() {
        // when
        imageService.isDuplicated("hashName");

        // then
        verify(imageDao).isDuplicated("hashName");
    }

    @Test
    void testIsReference() {
        // when
        imageService.isReference("hashName");

        // then
        verify(imageDao).isReference("hashName");
    }
}