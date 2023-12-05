package com.celeb.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Schema(description = "사용자 이메일을 입력해주세요", example = "hun@naver.com")
    String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Schema(description = "사용자 비밀번호를 입력해주세요", example = "hun1214")
    String password;
}
