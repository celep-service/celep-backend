package com.celeb.user;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import com.celeb.security.jwt.JwtTokenUtil;
import com.celeb.security.jwt.Token;
import com.celeb.security.jwt.TokenCacheRepository;
import com.celeb.security.jwt.TokenDto;
import com.celeb.security.userDetails.CustomUserDetails;
import io.micrometer.common.util.StringUtils;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    // token 저장소를 redis로 변경.
    private final TokenCacheRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    public UserDto createUser(UserDto userDto) {

        if (StringUtils.isEmpty(userDto.getEmail())) {
            throw new GeneralException(Code.EMPTY_EMAIL);
        }
        if (StringUtils.isEmpty(userDto.getName())) {
            throw new GeneralException(Code.EMPTY_NAME);
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new GeneralException(Code.ALREADY_EXISTS_USER);
        }
        if (StringUtils.isEmpty(userDto.getPassword())) {
            throw new GeneralException(Code.EMPTY_PASSWORD);
        }
        if (StringUtils.isEmpty(userDto.getGender())) {
            throw new GeneralException(Code.EMPTY_GENDER);
        }

        User user = userDto.toEntity();

        String encode_password = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode_password);

        User savedUser = userRepository.save(user);
        return UserDto.userSignUpResponse(savedUser);
    }

    public TokenDto login(LoginRequestDto loginRequestDto) {

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new GeneralException(Code.EMPTY_USER);
        }
        if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new GeneralException(Code.INVALID_PASSWORD);
        }

        TokenDto tokenDto = jwtTokenUtil.generateJwt(user.get());

        // TODO: refresh token 저장

        Token token = Token.builder()
            .userId(user.get().getId())
            .refreshToken(tokenDto.getRefreshToken())
            .expiredAt(tokenDto.getRefreshTokenExpiresIn())
            .build();

        // 기존 토큰이 있으면 삭제할 필요가 없다.
//        tokenRepository.findByUserId(user.get().getId())
//            .ifPresent(tokenRepository::delete);

        tokenRepository.save(token);

        return tokenDto;
    }

    public User getLoginUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new GeneralException(Code.EMPTY_USER);
        }

        return user.get();
    }

    public TokenDto reissue(ReissueRequestDto reissueRequestDto) {
        // 서비스에서 바디값으로 토큰을 받고 jwtTokenUtil.validateToken을 호출하면 에러 리턴이 제대로 되지 않는다.
        // validateToken에서는 header값을 캐치하고 filter단에서 에러가 발생하면 처리하는데,
        // 이미 서비스단으로 넘어왔기에 에러가 발생하더라도 jwt exception과 filter에서 제대로 처리되지 않는 듯 하다.
        // 여기서는 바디에서 받아서 처리하므로 다른 방법이 필요하다.
        // -> 그래서 아래와 같이 GeneralException을 직접 던지도록 변경.

        // redis로 변경하면서 필요없어진 코드.
        // refresh 토큰을 바로 repo에서 찾는다.
        // 그리고 만료기간도 redis에서 자동으로 처리되므로, 만료기간을 체크할 필요가 없다.
//        jwtTokenUtil.ValidateRefreshToken(reissueRequestDto.getRefreshToken());

        log.info("reissueRequestDto.getRefreshToken() = {}", reissueRequestDto.getRefreshToken());
        log.info("repo: {}",
            tokenRepository.findById(reissueRequestDto.getRefreshToken()).toString());
        Token jwt = tokenRepository.findById(reissueRequestDto.getRefreshToken())
            .orElseThrow(() -> new GeneralException(Code.REFRESH_TOKEN_NOT_FOUND));
        if (!jwt.getRefreshToken().equals(reissueRequestDto.getRefreshToken())) {
            throw new GeneralException(Code.INVALID_REFRESH_TOKEN);
        }

        tokenRepository.delete(jwt);

        Authentication authentication = jwtTokenUtil.getAuthentication(
            reissueRequestDto.getRefreshToken());
        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();
        TokenDto newJwtDto = jwtTokenUtil.generateJwt(user);
        Token newJwt = Token.builder().userId(user.getId())
            .refreshToken(newJwtDto.getRefreshToken()).build();
        tokenRepository.save(newJwt);

        return newJwtDto;

    }

    public UserDto myInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer currentUserId = ((CustomUserDetails) authentication.getPrincipal()).getUserId();

        User user = userRepository.findById(currentUserId)
            .orElseThrow(() -> new GeneralException(Code.NOT_FOUND_USER));

        return UserDto.myInfoResponse(user);
    }
}
