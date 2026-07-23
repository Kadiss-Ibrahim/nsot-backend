package com.sielmed.nsotbackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration; // access token — courte durée, ex: 900000 (15 min)

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration; // refresh token — longue durée, ex: 604800000 (7 jours)

    public String generateToken(String username, String role) {
        return buildToken(username, role, jwtExpiration, "access");
    }

    public String generateRefreshToken(String username, String role) {
        return buildToken(username, role, refreshExpiration, "refresh");
    }

    private String buildToken(String username, String role, long expiration, String type) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", type)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    public String extractTokenType(String token) {
        return getClaims(token).get("type", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}