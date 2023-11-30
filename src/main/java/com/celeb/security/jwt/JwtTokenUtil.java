package com.celeb.security.jwt;

import com.celeb._base.constant.Code;
import com.celeb.security.CustomUserDetails;
import com.celeb.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenUtil {


    private static final String secretKey = "cyd-celep-donghun-changhyun-eunchae";

    private final CustomUserDetailsService customUserDetailsService;

    public static String createAccessToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        long expireTimeMs = 250 * 60 * 60; //15분
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public static String createRefreshToken(String email) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        long expireTimeMs = 1000 * 60 * 60 * 24 * 14; // 14일
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
    public Authentication getAuthentication(String token) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", Collections.emptyList());
    }

    public static String getEmail(String token) {
        return extractClaims(token).get("email").toString();
    }

    public boolean validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        System.out.println("hello");
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new JwtException(Code.EXPIRED_TOKEN.getMessage());
        }
    }

    private static Claims extractClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
