package com.celeb.security.jwt;

import lombok.Data;

@Data
public class Token {
    String accessToken;
    String refreshToken;

    public Token(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
