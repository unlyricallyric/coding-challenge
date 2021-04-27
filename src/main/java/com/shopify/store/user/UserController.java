package com.shopify.store.user;

import com.shopify.store.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    private static final List<User> USERS = Arrays.asList(
            new User(1, "James"),
            new User(2, "Maria"),
            new User(3, "Anna")
    );

    @GetMapping("{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return USERS.stream()
                .filter(user -> userId.equals(user.getUserId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("user " + userId + " does not exist"));
    }
}
