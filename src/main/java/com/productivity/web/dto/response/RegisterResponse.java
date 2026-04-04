package com.productivity.web.dto.response;

import lombok.Data;

@Data
public class RegisterResponse {
    private Long id;
    private String email;
    private String displayName;
    private String token;

    public RegisterResponse() {}

    public RegisterResponse(Long id, String email, String displayName, String token) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.token = token;
    }
}
