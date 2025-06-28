package com.wizard.ViperFlex.config;

import com.wizard.ViperFlex.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;



    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        System.out.println("Using secret key: " + secretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*30))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);


    }

    public String extractUserName(String token) {
        return extractAllClaims(token).getSubject();    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();


        } catch (SignatureException e) {
            throw new RuntimeException("JWT signature does not match", e);
        }
    }

    public boolean validateToken(String token, User user) {
        final String userName = extractUserName(token);
        return (userName.equals(user.getEmail()) && !isTokenExpired(token));  // Validate the token
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());  // Check if token has expired
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);  // Extract expiration date from token
    }
}
