package com.celeb.user;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        if(user.isEmpty()){
            throw new GeneralException(Code.EMPTY_USER);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user.get());

        return customUserDetails;
    }
}
