package com.celeb.user;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import com.celeb.security.jwt.JwtTokenUtil;
import io.micrometer.common.util.StringUtils;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

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

    public String login(LoginRequestDto loginRequestDto){

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()){
            throw new GeneralException(Code.EMPTY_USER);
        }
        if(!passwordEncoder.matches(password, user.get().getPassword())){
            throw new GeneralException(Code.INVALID_PASSWORD);
        }

        return JwtTokenUtil.createToken(user.get().getEmail());
    }

    public User getLoginUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);

        if(user.isEmpty()){
            throw new GeneralException(Code.EMPTY_USER);
        }

        return user.get();
    }
}
