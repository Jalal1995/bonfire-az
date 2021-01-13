package com.bonfire.az.bonfireaz.controller;

import com.bonfire.az.bonfireaz.model.entity.XUser;
import com.bonfire.az.bonfireaz.model.request.LoginRq;
import com.bonfire.az.bonfireaz.model.request.RegRq;
import com.bonfire.az.bonfireaz.model.response.LoginRs;
import com.bonfire.az.bonfireaz.model.response.RegRs;
import com.bonfire.az.bonfireaz.model.response.UserRs;
import com.bonfire.az.bonfireaz.service.AuthService;
import com.bonfire.az.bonfireaz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper mapper;

    @PostMapping("reg")
    public ResponseEntity<?> register(@RequestBody RegRq rq) {

        if (userService.isUserExists(rq.getEmail())) {
            return ResponseEntity
                    .status(BAD_REQUEST)
                    .body(RegRs.USER_EXISTS(rq.getEmail()));
        }
        XUser xuser = userService.register(rq.getEmail(), rq.getPassword(), rq.getName());
        UserRs user = mapper.map(xuser, UserRs.class);
        RegRs regRs = authService.login(rq.getEmail(), rq.getPassword())
                .map(t -> new RegRs(0, t, user))
                .orElse(new RegRs(-1, "", null));
        return ResponseEntity
                .ok(regRs);
    }

    @PostMapping("login")
    public LoginRs authorize(@RequestBody LoginRq rq) {

        return authService.login(rq.getEmail(), rq.getPassword())
                .map(t -> new LoginRs(0, t, mapper.map(userService.findXUserByEmail(rq.getEmail()), UserRs.class)))
                .orElse(new LoginRs(-1, "", null));
    }
}
