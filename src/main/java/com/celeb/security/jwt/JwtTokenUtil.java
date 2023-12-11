package com.celeb.security.jwt;

import com.celeb.security.CustomUserDetails;
import com.celeb.security.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class JwtTokenUtil {


    //private static final String secretKey = "cyd-celep-donghun-changhyun-eunchae";


    private final String secretKey;

    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenUtil(@Value("${jwt.secret}") String secretKey,
        CustomUserDetailsService customUserDetailsService){
        this.secretKey = secretKey;
        this.customUserDetailsService = customUserDetailsService;
    }

    public String createAccessToken(String email) {
        log.info("secretKey: {}", secretKey);
        System.out.println("secretKey: " + secretKey);
        Claims claims = Jwts.claims();
        claims.put("email", email);
        long expireTimeMs = 4 * 1000 * 60 * 60; // 4시간
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

    public String getEmail(String token) {
        return extractClaims(token).get("email").toString();
    }

    public boolean validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new JwtException("만료된 JWT 토큰입니다");
        } catch (SignatureException e){
            throw new JwtException("시그니처 검증에 실패한 JWT 토큰입니다");
        } catch (MalformedJwtException e){
            throw new JwtException("손상된 JWT 토큰입니다");
        }
    }

    private Claims extractClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
