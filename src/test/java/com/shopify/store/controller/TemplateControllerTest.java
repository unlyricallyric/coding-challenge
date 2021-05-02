package com.shopify.store.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.shopify.store.dao.ImageDao;
import com.shopify.store.dao.ImageDataAccessService;
import com.shopify.store.service.ImageService;

import java.util.ArrayList;

import org.apache.catalina.connector.CoyotePrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;

@ContextConfiguration(classes = {TemplateController.class})
@ExtendWith(SpringExtension.class)
public class TemplateControllerTest {
    @MockBean
    private ImageService imageService;

    @Autowired
    private TemplateController templateController;

    @Test
    public void testGetLoginView() {
        assertEquals("login", (new TemplateController(new ImageService(new ImageDataAccessService()))).getLoginView());
    }

    @Test
    public void testGetAddImageView() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/addImage");
        MockMvcBuilders.standaloneSetup(this.templateController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("image"))
                .andExpect(MockMvcResultMatchers.view().name("add_image"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("add_image"));
    }

    @Test
    public void testGetImagesView() {
        TemplateController templateController = new TemplateController(new ImageService(new ImageDataAccessService()));
        ConcurrentModel model = new ConcurrentModel();
        assertEquals("all_images", templateController.getImagesView(model, new CoyotePrincipal("Name")));
    }

    @Test
    public void testGetImagesView2() {
        ImageService imageService = mock(ImageService.class);
        when(imageService.getAllAvailableImages(any())).thenReturn(new ArrayList<>());
        TemplateController templateController = new TemplateController(imageService);
        ConcurrentModel model = new ConcurrentModel();
        assertEquals("all_images", templateController.getImagesView(model, new CoyotePrincipal("Name")));
        verify(imageService).getAllAvailableImages(any());
    }

    @Test
    public void testGetMyImagesView() {
        ImageDao imageDao = mock(ImageDao.class);
        when(imageDao.getMyImages(anyString())).thenReturn(new ArrayList<>());
        TemplateController templateController = new TemplateController(new ImageService(imageDao));
        ConcurrentModel model = new ConcurrentModel();
        assertEquals("my_images", templateController.getMyImagesView(model, new CoyotePrincipal("Name")));
        verify(imageDao).getMyImages(anyString());
    }

    @Test
    public void testGetAddImageView2() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/addImage", "Uri Vars");
        MockMvcBuilders.standaloneSetup(this.templateController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("image"))
                .andExpect(MockMvcResultMatchers.view().name("add_image"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("add_image"));
    }
}

