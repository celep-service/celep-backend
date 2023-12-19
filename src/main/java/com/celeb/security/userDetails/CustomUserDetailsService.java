package com.celeb.security.userDetails;

import com.celeb._base.constant.Code;
import com.celeb._base.exception.GeneralException;
import com.celeb.user.User;
import com.celeb.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userId) {
        Optional<User> user = userRepository.findById(Integer.parseInt(userId));
        if (user.isEmpty()) {
            throw new GeneralException(Code.EMPTY_USER);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(user.get());

        return customUserDetails;
    }
}
