package com.bonfire.az.bonfireaz.controller;

import com.bonfire.az.bonfireaz.entity.api.login.LoginRq;
import com.bonfire.az.bonfireaz.entity.api.login.LoginRs;
import com.bonfire.az.bonfireaz.entity.api.register.RegRq;
import com.bonfire.az.bonfireaz.entity.api.register.RegRs;
import com.bonfire.az.bonfireaz.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("reg")
    public ResponseEntity<?> register(@RequestBody RegRq rq) {

        if (authService.isUserExists(rq.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(RegRs.USER_EXISTS(rq.getEmail()));
        }
        RegRs regRs = authService.register(rq.getEmail(), rq.getPassword(), rq.getName())
                .map(t -> new RegRs(0, t))
                .orElse(new RegRs(-1, ""));
        return ResponseEntity
                .ok(regRs);
    }

    @PostMapping("login")
    public LoginRs authorize(@RequestBody LoginRq rq) {
        return authService.login(rq.getEmail(), rq.getPassword())
                .map(t -> new LoginRs(0, t))
                .orElse(new LoginRs(-1, ""));
    }

    @GetMapping("test")
    public String access() {
        return "access test yes!";
    }
}
