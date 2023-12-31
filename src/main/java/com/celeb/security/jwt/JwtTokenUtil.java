package com.celeb.security.jwt;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import com.celeb.security.userDetails.CustomUserDetails;
import com.celeb.security.userDetails.CustomUserDetailsService;
import com.celeb.user.User;
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
        CustomUserDetailsService customUserDetailsService) {
        this.secretKey = secretKey;
        this.customUserDetailsService = customUserDetailsService;
    }

    public String createAccessToken(User user, long expireTimeMs) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
            .setSubject(user.getId().toString())
            .claim("email", user.getEmail())
            .claim("name", user.getName())
            .claim("role", user.getRole())
            .claim("name", user.getName())
            .claim("token_type", "access")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String createRefreshToken(User user, long expireTimeMs) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
            .setSubject(user.getId().toString())
            .claim("token_type", "refresh")
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public TokenDto generateJwt(User user) {
        long now = (new Date()).getTime();
        long refreshTokenExpireTimeMs = 1000 * 60 * 60 * 24 * 14L; // 14일
        // long refreshTokenExpireTimeMs = 1000 * 60 * 1; // 1분
        long accessTokenExpireTimeMs = 1000 * 60 * 30; // 밀리초 * 분 * 시 -> 30분
        // long accessTokenExpireTimeMs = 1000 * 30 * 1; // 30초
        String accessToken = createAccessToken(user, accessTokenExpireTimeMs);
        String refreshToken = createRefreshToken(user, refreshTokenExpireTimeMs);

        return TokenDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .refreshTokenExpiresIn(refreshTokenExpireTimeMs)
            .build();
    }

    public Authentication getAuthentication(String token) {
        CustomUserDetails userDetails = customUserDetailsService.loadUserByUsername(
            getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", Collections.emptyList());
    }

    //    public String getEmail(String token) {
//        return extractClaims(token).get("email").toString();
//    }
    public String getUserId(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token);

            if (!claims.getBody().get("token_type").equals("access")) {
                throw new JwtException(Code.NOT_ACCESS_TOKEN.getMessage());
            }

            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new JwtException(Code.EXPIRED_TOKEN.getMessage());
        } catch (SignatureException e) {
            throw new JwtException(Code.NOT_SIGNATURE_TOKEN.getMessage());
        } catch (MalformedJwtException e) {
            throw new JwtException(Code.MALFORMED_TOKEN.getMessage());
        } catch (JwtException e) {
            throw new JwtException(e.getMessage());
        }
    }

    public boolean ValidateRefreshToken(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new GeneralException(Code.EXPIRED_TOKEN);
        } catch (SignatureException e) {
            throw new GeneralException(Code.NOT_SIGNATURE_TOKEN);
        } catch (MalformedJwtException e) {
            throw new GeneralException(Code.MALFORMED_TOKEN);
        }


    }

    private Claims extractClaims(String token) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

}
