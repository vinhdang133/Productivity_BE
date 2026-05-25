package com.productivity.web.service;

import com.productivity.web.entity.Account;
import com.productivity.web.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {



    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {

        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!account.isActive()) {
            throw new DisabledException("Account is deactivated");
        }
        return User.builder()
                .username(account.getEmail())
                .password(account.getPasswordHash())
                .roles("USER")
                .build();
    }


}
