package com.celeb.security.jwt;

import lombok.Data;

@Data
public class Token {
    String accessToken;

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }
}
