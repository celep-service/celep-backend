package com.celeb.user;

import com.celeb._base.dto.DataResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "회원 관련 API", description = "회원 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원정보 생성", description = "회원 정보를 생성합니다.")
    @PostMapping("")
    public DataResponseDto<Object> createUsers(@Valid @RequestBody UserDto userDto) {
        return DataResponseDto.of(userService.createUser(userDto));
    }

    @Operation(summary = "로그인 토큰 생성", description = "Access Token을 생성합니다.")
    @PostMapping("/login")
    public DataResponseDto<Object> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return DataResponseDto.of(userService.login(loginRequestDto));
    }
}
