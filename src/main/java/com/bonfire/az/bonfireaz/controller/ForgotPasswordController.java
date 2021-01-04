package com.bonfire.az.bonfireaz.controller;

import com.bonfire.az.bonfireaz.model.entity.PasswordResetToken;
import com.bonfire.az.bonfireaz.model.entity.XUser;
import com.bonfire.az.bonfireaz.model.request.ForgotRq;
import com.bonfire.az.bonfireaz.model.response.OperationModel;
import com.bonfire.az.bonfireaz.service.EmailService;
import com.bonfire.az.bonfireaz.service.ResetPasswordService;
import com.bonfire.az.bonfireaz.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import static com.bonfire.az.bonfireaz.controller.OperationName.*;
import static com.bonfire.az.bonfireaz.model.response.OperationStatus.*;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/password")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {

    private final UserService userService;
    private final ResetPasswordService passService;
    private final EmailService emailService;

    @PostMapping("forgot")
    public OperationModel forgot(@RequestParam String email) {
        if (!userService.isUserExists(email))
            return new OperationModel(
                    ERROR,
                    FORGOT_PASSWORD,
                    "no user found with email: " + email);

        String token = passService.createToken(email);
        emailService.sendMail(email, token);
        return new OperationModel(
                SUCCESS,
                FORGOT_PASSWORD,
                "an email has been sent to: " + email);
    }

    @PostMapping("reset")
    public OperationModel reset(@RequestBody ForgotRq forgotReq) {
        Optional<PasswordResetToken> opPasswordResetToken =
                passService.findOpPasswordResetToken(forgotReq.getToken());
        if (!opPasswordResetToken.isPresent()) {
            return new OperationModel(
                    ERROR,
                    RESET_PASSWORD,
                    "token not found: " + forgotReq.getToken());
        }
        PasswordResetToken passwordResetToken = opPasswordResetToken.get();
        XUser user = passwordResetToken.getUser();
        userService.changePassword(user, forgotReq.getPassword());
        passService.delete(passwordResetToken);
        return new OperationModel(
                SUCCESS,
                RESET_PASSWORD,
                "password is changed");
    }
}
