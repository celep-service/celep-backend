package com.celeb.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @Hidden
    private Integer id;

    @NotEmpty(message = "이름을 입력해주세요.")
    @Schema(description = "사용자 이름을 입력해주세요", example = "hun")
    private String name;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Schema(description = "사용자 이메일을 입력해주세요", example = "hun@naver.com")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Schema(description = "사용자 비밀번호를 입력해주세요", example = "hun1214")
    private String password;

    @NotEmpty(message = "성별을 입력해주세요.")
    @Pattern(regexp = "MALE|FEMALE", message = "성별은 MALE, FEMALE만 가능합니다.")
    @Schema(description = "성별을 입력해주세요.", example = "MALE")
    private String gender;

    public User toEntity() {
        return User.builder()
            .name(this.name)
            .email(this.email)
            .password(this.password)
            .gender(this.gender)
            .build();
    }

    public static UserDto userSignUpResponse(User user) {
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .gender(user.getGender())
//            .password(user.getPassword())
            .build();
    }

}
