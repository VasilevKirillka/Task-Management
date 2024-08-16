package com.example.Task_Management.security.jwt;

import com.example.Task_Management.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtils {
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.expired}")
    private Long jwtLifeTime;

    private final UserDetailsServiceImpl userService;

    public String generateToken(String email) {
        var userDetails = userService.loadUserByUsername(email);
        Map<String, Object> claims = new HashMap<>();
        var roleList = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        claims.put("roles", roleList);
        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtLifeTime))
                .signWith(HS512, secret)
                .compact();
    }

    public boolean validate(String token) {
        try {
            getToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Срок действия токена закончился: {}", e.getMessage());
        }
        return false;
    }

    private Claims getToken(String token) {
        return Jwts.parser().setSigningKey(secret).build().parseClaimsJws(token).getBody();
    }

    public String getEmail(String token) {
        return getToken(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getToken(token).get("roles", List.class);
    }
}
