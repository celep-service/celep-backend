package com.celeb.user;

import com.celeb._base.dto.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "회원 관련 API", description = "회원 관련 API")
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원정보 생성", description = "회원 정보를 생성합니다.")
    @PostMapping("")
    public DataResponseDto<Object> createUsers(@Valid @RequestBody UserDto userDto) {
        return DataResponseDto.of(userService.createUser(userDto));
    }

    @Operation(summary = "로그인 토큰 생성", description = "Access, Refresh Token을 생성합니다.")
    @PostMapping("/login")
    public DataResponseDto<Object> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return DataResponseDto.of(userService.login(loginRequestDto));
    }

    // 리프레시 토큰으로도 post 등이 가능한 문제
    @Operation(summary = "리프레시 토큰으로 액세스 토큰 재발급 ", description = "리프레시 토큰으로 액세스 토큰을 재발급합니다.")
    @PostMapping("/reissue")
    public DataResponseDto<Object> reissue(
        @RequestBody ReissueRequestDto reissueRequestDto) {
        return DataResponseDto.of(userService.reissue(reissueRequestDto));
    }

    @Operation(summary = "마이페이지 조회", description = "프로필, 이름, 성별 정보를 반환합니다")
    @GetMapping("/me")
    public DataResponseDto<Object> myInfo(){
        return DataResponseDto.of(userService.myInfo());
    }
}
