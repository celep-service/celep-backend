package com.cody.springcody.user;

import com.cody.springcody._base.dto.DataResponseDto;
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

}
