package com.bonfire.az.bonfireaz.service;

import com.bonfire.az.bonfireaz.entity.db.XUser;
import com.bonfire.az.bonfireaz.entity.sec.XUserDetails;
import com.bonfire.az.bonfireaz.jwt.Const;
import com.bonfire.az.bonfireaz.jwt.JwtService;
import com.bonfire.az.bonfireaz.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager am;
    private final JwtService jwt;
    private final UserRepo repo;
    private final PasswordEncoder enc;

    public Optional<String> register(String email, String password, String name) {
        repo.save(new XUser(email, enc.encode(password), name, new String[] {"USER"}));
        return login(email, password);
    }

    public boolean isUserExists(String email) {
        return repo.findByEmail(email).isPresent();
    }

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
