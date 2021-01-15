package com.bonfire.az.bonfireaz.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("oauth2")
//@CrossOrigin("*")
public class OAuthController {

    @GetMapping("some")
    public String oauth2() {
        return "test oauth2 cors";
    }

}
