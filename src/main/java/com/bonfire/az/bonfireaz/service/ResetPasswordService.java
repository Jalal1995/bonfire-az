package com.bonfire.az.bonfireaz.service;

import com.bonfire.az.bonfireaz.exception.InvalidLinkException;
import com.bonfire.az.bonfireaz.exception.TokenNotFoundException;
import com.bonfire.az.bonfireaz.model.entity.PasswordResetToken;
import com.bonfire.az.bonfireaz.model.entity.XUser;
import com.bonfire.az.bonfireaz.repo.PasswordTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResetPasswordService {

    private final PasswordTokenRepo passTokenRepo;
    private final UserService userService;

    public String createToken(String email) {
        XUser user = userService.findXUserByEmail(email);
        findOpTokenByEmail(user.getEmail()).ifPresent(this::delete);
        PasswordResetToken token = passTokenRepo.save(new PasswordResetToken(user));
        return token.getToken();
    }

    public Optional<PasswordResetToken> findOpTokenByEmail(String email) {
        return passTokenRepo.findByUser(userService.findXUserByEmail(email));
    }

    public Optional<PasswordResetToken> findOpPasswordResetToken(String token) {
        return passTokenRepo.findByToken(token);
    }

    public void delete(PasswordResetToken token) {
        token.setUser(null);
        passTokenRepo.save(token);
        passTokenRepo.delete(token);
    }
}
