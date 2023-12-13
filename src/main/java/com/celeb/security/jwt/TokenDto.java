package com.celeb.security.jwt;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpiresIn;

}
