package com.productivity.web.controller;

import com.productivity.web.dto.request.LoginRequest;
import com.productivity.web.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        // TODO: validate user (DB)

        return jwtService.generateToken(request.getUsername());
    }
}
