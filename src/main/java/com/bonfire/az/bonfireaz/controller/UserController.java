package com.bonfire.az.bonfireaz.controller;

import com.bonfire.az.bonfireaz.entity.response.UserDto;
import com.bonfire.az.bonfireaz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @GetMapping("users/{userId}")
    public UserDto getUser(@PathVariable String userId) {
        return userService.findByUserId(userId);
    }

}
