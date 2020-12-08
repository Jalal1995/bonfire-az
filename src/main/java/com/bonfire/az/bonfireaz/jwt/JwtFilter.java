package com.bonfire.az.bonfireaz.jwt;

import com.bonfire.az.bonfireaz.conf.UserDetailsServiceJPA;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Log4j2
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwt;
    private final UserDetailsServiceJPA uds;

    public JwtFilter(JwtService jwt, UserDetailsServiceJPA uds) {
        this.jwt = jwt;
        this.uds = uds;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            extractToken(request)
                    .flatMap(jwt::tokenToClaims)
                    .map(jwt::claimsToId)
                    .map(uds::loadUserByUserId)
                    .map(ud -> new UsernamePasswordAuthenticationToken(ud, null, ud.getAuthorities()))
                    .ifPresent(auth -> {
                        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    });

            chain.doFilter(request, response);
        } catch (Exception x) {
            log.error(x.getMessage());
        }
    }

    private Optional<String> extractToken(HttpServletRequest req) {
        return Optional.ofNullable(req.getHeader(Const.HEADER))
                .filter(auth -> auth.startsWith(Const.PREFIX))
                .map(auth -> auth.substring(Const.PREFIX.length()));
    }

}
