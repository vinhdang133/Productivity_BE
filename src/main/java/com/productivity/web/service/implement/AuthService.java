package com.productivity.web.service.implement;


import com.productivity.web.dto.request.LoginRequest;
import com.productivity.web.dto.request.RegisterRequest;
import com.productivity.web.dto.response.RegisterResponse;
import com.productivity.web.entity.Account;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.service.AuthenServiceInterface;
import com.productivity.web.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthenServiceInterface {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // include default role USER in token
        return jwtService.generateToken(request.getEmail(), "USER");

    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        // Check existing
        accountRepository.findByEmail(request.getEmail()).ifPresent(a -> {
            throw new IllegalArgumentException("Email already in use");
        });

        Account account = Account.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .displayName(request.getDisplayName() == null || request.getDisplayName().isBlank() ? request.getEmail() : request.getDisplayName())
                .isActive(true)
                .build();

        Account saved = accountRepository.save(account);

        String token = jwtService.generateToken(saved.getEmail(), "USER");

        return new RegisterResponse(saved.getId(), saved.getEmail(), saved.getDisplayName(), token);
    }
}
