package com.cody.springcody.user;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    private Integer id;
    private String name;
    private String email;
    private String password;
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
