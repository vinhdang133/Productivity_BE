package com.productivity.web.service;


import com.productivity.web.dto.request.LoginRequest;

public interface AuthenServiceInterface  {
     String login(LoginRequest loginRequest);
}
