package com.bonfire.az.bonfireaz.jwt;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
@PropertySource("classpath:jwt.properties")
public class JwtService {

    @Value("${jwt.expiry}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(Integer user_id) {
        final Date now = new Date();
        final Date expiry = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .setSubject(user_id.toString())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Optional<Jws<Claims>> tokenToClaims(String token) {
        try {
            return Optional.of(Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token));
        } catch (SignatureException ex) {
            log.error("JWT: Invalid signature");
        } catch (MalformedJwtException ex) {
            log.error("JWT: Invalid token");
        } catch (ExpiredJwtException ex) {
            log.error("JWT: Expired token");
        } catch (UnsupportedJwtException ex) {
            log.error("JWT: Unsupported token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT: token is empty.");
        }
        return Optional.empty();
    }

    public int claimsToId(Jws<Claims> claims) {
        return Integer.parseInt(claims.getBody().getSubject());
    }
}
