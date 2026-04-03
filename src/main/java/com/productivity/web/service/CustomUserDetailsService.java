package com.productivity.web.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) {
        // TODO: load từ DB (Account)
        return User.builder()
                .username(username)
                .password("123") // temp
                .roles("USER")
                .build();
    }
}
