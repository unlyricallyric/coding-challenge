package com.shopify.store.controller;

import com.shopify.store.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("manage/api/v1/user")
public class UserManagementController {

    private static final List<User> USERS = Arrays.asList(
            new User(1, "James"),
            new User(2, "Maria"),
            new User(3, "Anna")
    );

    @GetMapping()
    public List<User> getAllUsers() {
        return USERS;
    }

    @PostMapping
    public void registerNewUser(@RequestBody User user) {
        System.out.println(user);
    }

    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        System.out.println(id);
    }

    @PutMapping("{id}")
    public void updateUser(@PathVariable("id") Integer id, User user) {
        System.out.println(String.format("%s %s", id, user));
    }
}
