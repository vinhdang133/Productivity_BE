package com.productivity.web.dto.request;

import lombok.Data;

@Data
public class UpdateAccountRequest {
    private String displayName;
    private String avatarUrl;
}
