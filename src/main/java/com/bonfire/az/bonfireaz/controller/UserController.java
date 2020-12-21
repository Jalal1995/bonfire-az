package com.bonfire.az.bonfireaz.controller;

import com.bonfire.az.bonfireaz.model.response.UserDto;
import com.bonfire.az.bonfireaz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("{userId}")
    public UserDto getUser(@PathVariable String userId) {
        return userService.findByUserId(userId);
    }

}
