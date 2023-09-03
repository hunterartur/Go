package com.simbirsoft.gateway.service;

import com.simbirsoft.gateway.client.AuthClient;
import com.simbirsoft.gateway.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProcessor {

    private final AuthClient authClient;

    private String verifyingKey;

    public JwtTokenProcessor(AuthClient authClient) {
        this.authClient = authClient;
        verifyingKey = authClient.getVerifyingKey();
    }

    public boolean validateToken(String token) {
        return getClaimsFromTokenWithRetry(token)
                .getBody()
                .getExpiration()
                .after(new Date());
    }

    public String getIdUserFromToken(String token) {
        var claims = getClaimsFromTokenWithRetry(token);
        return claims.getBody().getId();
    }

    private Jws<Claims> getClaimsFromTokenWithRetry(String token) {
        try {
            return getClaimsFromToken(token);
        } catch (Exception exception) {
            verifyingKey = authClient.getVerifyingKey();
            try {
                return getClaimsFromToken(token);
            } catch (Exception ex) {
                throw new UnauthorizedException("Не авторизован!", ex);
            }
        }
    }

    private Jws<Claims> getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(verifyingKey.getBytes()).getBytes()))
                .build()
                .parseClaimsJws(token);
    }
}
