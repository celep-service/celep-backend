package com.cody.springcody.user;

import com.cody.springcody._base.constant.Code;
import com.cody.springcody._base.exception.GeneralException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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
        // add more validation

        User user = userDto.toEntity();
        User savedUser = userRepository.save(user);
        return UserDto.userSignUpResponse(savedUser);
    }
}
