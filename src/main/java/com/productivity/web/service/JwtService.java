package com.productivity.web.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret:Zm9vYmFyYmF6cXV4c2FtcGxlc2VjcmV0MTIzNDU2Nzg5MGFiYw==}")
    private String SECRET_KEY_BASE64;

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY_BASE64);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username, String... roles) {

        Map<String, Object> claims = new HashMap<>();

        if (roles != null && roles.length > 0) {
            claims.put("roles", Arrays.asList(roles));
        }

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60))
                .signWith(getSignKey())
                .compact();
    }
    // Backwards-compatible single-arg generateToken
    public String generateToken(String username) {
        return generateToken(username, new String[]{});
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List) {
            //noinspection unchecked
            return ((List<Object>) rolesObj).stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public boolean isValid(String token, String username) {
        try {
            return extractUsername(token).equals(username) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
