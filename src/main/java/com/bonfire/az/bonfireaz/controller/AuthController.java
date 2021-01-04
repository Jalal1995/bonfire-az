package com.bonfire.az.bonfireaz.controller;

import com.bonfire.az.bonfireaz.model.request.LoginRq;
import com.bonfire.az.bonfireaz.model.request.RegRq;
import com.bonfire.az.bonfireaz.model.response.LoginRs;
import com.bonfire.az.bonfireaz.model.response.RegRs;
import com.bonfire.az.bonfireaz.service.AuthService;
import com.bonfire.az.bonfireaz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("reg")
    public ResponseEntity<?> register(@RequestBody RegRq rq) {

        if (userService.isUserExists(rq.getEmail())) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(RegRs.USER_EXISTS(rq.getEmail()));
        }
        String userId = authService.register(rq.getEmail(), rq.getPassword(), rq.getName());
        RegRs regRs = authService.login(rq.getEmail(), rq.getPassword())
                .map(t -> new RegRs(0, t, userId))
                .orElse(new RegRs(-1, "", userId));
        return ResponseEntity
                .ok(regRs);
    }

    @PostMapping("login")
    public LoginRs authorize(@RequestBody LoginRq rq) {

        return authService.login(rq.getEmail(), rq.getPassword())
                .map(t -> new LoginRs(0, t, userService.findXUserByEmail(rq.getEmail()).getUserId()))
                .orElse(new LoginRs(-1, "", ""));
    }

    @GetMapping("test")
    public String access() {
        return "access test yes localhost!";
    }
}
