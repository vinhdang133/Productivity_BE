package com.productivity.web.service.implement;


import com.productivity.web.dto.request.LoginRequest;
import com.productivity.web.service.AuthenServiceInterface;
import com.productivity.web.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService  implements AuthenServiceInterface {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

         String token = jwtService.generateToken(request.getUsername());
         return token;

    }
}
