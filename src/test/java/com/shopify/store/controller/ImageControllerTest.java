package com.shopify.store.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.store.model.Image;
import com.shopify.store.service.ImageService;
import com.sun.security.auth.UserPrincipal;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.UUID;

import org.apache.catalina.connector.CoyotePrincipal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@ContextConfiguration(classes = {ImageController.class})
@ExtendWith(SpringExtension.class)
public class ImageControllerTest {
    @Autowired
    private ImageController imageController;

    @MockBean
    private ImageService imageService;

    @Test
    public void testAddImage() throws Exception {
        when(this.imageService.addImage(any())).thenReturn(2);

        Image img = new Image(
                UUID.randomUUID(),
                "original file name",
                "hashing",
                "johnson",
                true,
                "hashname"
        );
        String content = (new ObjectMapper()).writeValueAsString(img);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/image/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.imageController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("api/v1/image/add"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("api/v1/image/add"));
    }

    @Test
    public void testUploadImage() throws IOException {
        ImageService imageService = mock(ImageService.class);
        when(imageService.saveImage(any(), anyString(), anyBoolean()))
                .thenReturn(new ArrayList<>());
        ImageController imageController = new ImageController(imageService);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("Name",
                new ByteArrayInputStream("TEST_STRING".getBytes(StandardCharsets.UTF_8)));
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("Name",
                new ByteArrayInputStream("TEST_STRING".getBytes(StandardCharsets.UTF_8)));
        RedirectAttributesModelMap redirectAttrs = new RedirectAttributesModelMap();
        assertEquals("redirect:/addImage",
                imageController
                        .uploadImage(
                                new MultipartFile[]{mockMultipartFile, mockMultipartFile1,
                                        new MockMultipartFile("Name",
                                                new ByteArrayInputStream("TEST_STRING".getBytes(StandardCharsets.UTF_8)))},
                                "Image privacy", redirectAttrs, new CoyotePrincipal("Name")));
        verify(imageService).saveImage(any(), anyString(), anyBoolean());
    }

    @Test
    public void testDeleteImageById() throws Exception {
        when(this.imageService.deleteImageByUsernameAndImageId(anyString(), anyString())).thenReturn("foo");
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/api/v1/image/delete/{hashName}",
                "value");
        deleteResult.principal(new UserPrincipal("principal"));
        MockMvcBuilders.standaloneSetup(this.imageController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/my_images"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/my_images"));
    }
}

