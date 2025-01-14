package com.vi5hnu.auth_service.security;

import com.vi5hnu.auth_service.Entity.user.UserModel;
import com.vi5hnu.auth_service.services.user.UserRepository;
import com.vi5hnu.auth_service.services.user.UserService;
import com.vi5hnu.auth_service.specifications.UserSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserModel userModel=userRepository.findOne(UserSpecifications.activeUserById(userId)).orElseThrow(()->new UsernameNotFoundException("user does not exists."));
        return new SecurityUserDetails(userModel);
    }
}