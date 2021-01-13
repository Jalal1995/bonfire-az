package com.bonfire.az.bonfireaz.service;

import com.bonfire.az.bonfireaz.jwt.Const;
import com.bonfire.az.bonfireaz.jwt.JwtService;
import com.bonfire.az.bonfireaz.model.sec.XUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager am;
    private final JwtService jwt;

    public Optional<String> login(String email, String password) {
        Authentication auth = am.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return Optional.of(auth)
                .filter(Authentication::isAuthenticated)
                .map(a -> {
                    SecurityContextHolder.getContext().setAuthentication(a);
                    return a;
                })
                .map(a -> (XUserDetails) a.getPrincipal())
                .map(XUserDetails::getId)
                .map(jwt::generateToken)
                .map(t -> Const.PREFIX + t);
    }
}
