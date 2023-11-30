package com.celeb.security.jwt;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    public void save(String token){
        String email = JwtTokenUtil.getEmail(token);
        Optional<RefreshToken> refreshToken= refreshTokenRepository.findByEmail(email);
        if(refreshToken.isEmpty()){
            refreshTokenRepository.save(new RefreshToken(email, token));
        }
        else{
            refreshTokenRepository.save(refreshToken.get());
        }
    }

    public Optional<RefreshToken> findRefreshToken(String email){
        return refreshTokenRepository.findByEmail(email);
    }
}
