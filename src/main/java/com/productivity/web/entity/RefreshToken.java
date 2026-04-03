package com.productivity.web.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    private Long id;


    // Many refresh tokens → 1 user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",                   // tên FK column trong bảng này
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_rt_user")  // đặt tên FK
    )
    private Account user;

    private String token;

    private String deviceInfo;

    private LocalDateTime expiryAt;

    private boolean revoked;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
