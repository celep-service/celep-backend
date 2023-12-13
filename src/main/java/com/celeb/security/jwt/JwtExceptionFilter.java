package com.celeb.security.jwt;

import com.celeb._base.constant.Code;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    Map<String, String> map = new HashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        try{
            filterChain.doFilter(request, response);
        } catch (JwtException ex) {
            String message = ex.getMessage();
            //토큰 만료된 경우
            if(Code.EXPIRED_TOKEN.getMessage().equals(message)) {
                setResponse(response, Code.EXPIRED_TOKEN);
            }
            else if(Code.NOT_SUPPORTED_TOKEN.getMessage().equals(message)){
                setResponse(response, Code.NOT_SUPPORTED_TOKEN);
            }
            else if(Code.NOT_SIGNATURE_TOKEN.getMessage().equals(message)){
                setResponse(response, Code.NOT_SIGNATURE_TOKEN);
            }
            else if(Code.MALFORMED_TOKEN.getMessage().equals(message)){
                setResponse(response, Code.MALFORMED_TOKEN);
            }
        }
    }

    private void setResponse(HttpServletResponse response, Code errorCode)
        throws RuntimeException, IOException {
        JSONObject responseJson = new JSONObject();
        responseJson.put("isSuccess", false);
        responseJson.put("code", errorCode.getCode());
        responseJson.put("message", errorCode.getMessage());

        response.getWriter().print(responseJson);
    }
}
