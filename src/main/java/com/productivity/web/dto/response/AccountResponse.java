package com.productivity.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private Long id;
    private String email;
    private String displayName;
    private String avatarUrl;
    private boolean active;
    private LocalDateTime createdAt;
}
