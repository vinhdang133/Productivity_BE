package com.productivity.web.service;


import com.productivity.web.dto.request.LoginRequest;
import com.productivity.web.dto.request.RegisterRequest;
import com.productivity.web.dto.response.RegisterResponse;

public interface AuthenServiceInterface  {
     String login(LoginRequest loginRequest);

     RegisterResponse register(RegisterRequest registerRequest);

}
