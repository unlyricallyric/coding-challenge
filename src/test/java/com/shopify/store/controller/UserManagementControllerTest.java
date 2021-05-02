package com.shopify.store.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopify.store.model.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserManagementController.class})
@ExtendWith(SpringExtension.class)
public class UserManagementControllerTest {
    @Autowired
    private UserManagementController userManagementController;

    @Test
    public void testDeleteUser() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/manage/api/v1/user/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.userManagementController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteUser2() throws Exception {
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/manage/api/v1/user/{id}", 1);
        deleteResult.contentType("text/plain");
        MockMvcBuilders.standaloneSetup(this.userManagementController)
                .build()
                .perform(deleteResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/manage/api/v1/user");
        MockMvcBuilders.standaloneSetup(this.userManagementController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString(
                                "[{\"userId\":1,\"username\":\"James\"},{\"userId\":2,\"username\":\"Maria\"}" +
                                        ",{\"userId\":3,\"username\":\"Anna\"}]")));
    }

    @Test
    public void testGetAllUsers2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/manage/api/v1/user");
        getResult.contentType("TEST_STRING");
        MockMvcBuilders.standaloneSetup(this.userManagementController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString(
                                "[{\"userId\":1,\"username\":\"James\"},{\"userId\":2,\"username\":\"Maria\"}," +
                                        "{\"userId\":3,\"username\":\"Anna\"}]")));
    }

    @Test
    public void testRegisterNewUser() throws Exception {
        User user = new User();
        user.setUsername("janedoe");
        user.setUserId(123);
        String content = (new ObjectMapper()).writeValueAsString(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/manage/api/v1/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.userManagementController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString(
                                "[{\"userId\":1,\"username\":\"James\"},{\"userId\":2,\"username\":\"Maria\"}," +
                                        "{\"userId\":3,\"username\":\"Anna\"}]")));
    }

    @Test
    public void testUpdateUser() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/manage/api/v1/user/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.userManagementController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateUser2() throws Exception {
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/manage/api/v1/user/{id}", 1);
        putResult.contentType("TEST_STRING");
        MockMvcBuilders.standaloneSetup(this.userManagementController)
                .build()
                .perform(putResult)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

