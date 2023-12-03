package com.celeb.user;

import com.celeb._base.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("")
    public DataResponseDto<Object> getPosts(@RequestBody UserDto userDto) {
        return DataResponseDto.of(userService.createUser(userDto));
    }

    @PostMapping("/login")
    public DataResponseDto<Object> login(@RequestBody LoginRequestDto loginRequestDto){
        return DataResponseDto.of(userService.login(loginRequestDto));
    }
}
