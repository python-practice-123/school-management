package com.eduai.schoolmanagement.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduai.schoolmanagement.entity.User;
import com.eduai.schoolmanagement.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        if (!user.isActive()) {
            throw new UsernameNotFoundException("User account is inactive: " + email);
        }

        if (user.isLocked()) {
            throw new UsernameNotFoundException("User account is locked: " + email);
        }

        return UserDetailsImpl.build(user);
    }
}
