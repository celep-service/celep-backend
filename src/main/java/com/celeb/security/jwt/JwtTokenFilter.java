package com.celeb.security.jwt;

import com.celeb._base.constant.Code;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 로그인, reissue 요청은 토큰 검증을 하지 않는다.
        String requestUri = request.getRequestURI();
        if (requestUri.equals("/users/login") || requestUri.equals("/users/reissue")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!authorizationHeader.startsWith("Bearer ")) {
            throw new JwtException(Code.NOT_SUPPORTED_TOKEN.getMessage());
        }

        String accessToken = authorizationHeader.split(" ")[1];
        if (jwtTokenUtil.validateToken(accessToken)) {

            Authentication authentication = jwtTokenUtil.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}