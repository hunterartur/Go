package com.simbirsoft.mockauthserver.Util;

import com.simbirsoft.mockauthserver.property.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public String generateJwtToken(UUID id, String email) {
        var createdDate = new Date();
        var expirationDate = new Date(createdDate.getTime() + jwtProperties.expirationSecond() * 1000);
        return Jwts.builder()
                .setSubject(email)
                .setId(id.toString())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(Keys.hmacShaKeyFor(CoderBase64.encode(jwtProperties.secretKey()).getBytes()))
                .compact();
    }
}
