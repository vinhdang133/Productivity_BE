package com.productivity.web.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserGoal {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    //One to one
    // One-to-one — mỗi user có đúng 1 bộ goal
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true,  // đảm bảo mỗi user chỉ có 1 goal
            foreignKey = @ForeignKey(name = "fk_user_goal_user")
    )
    private Account user;

    @Column(name = "daily_focus_minutes")
    @Builder.Default
    private int     dailyFocusMinutes = 120;  // mặc định 25 phút mỗi ngày

    @Column(name = "daily_sessions_target")
    @Builder.Default
    private int dailySessionsTarget = 4;  // mặc định 4 session mỗi ngày

    @Column(name = "daily_tasks_target")
    @Builder.Default
    private int dailyTasksTarget = 3;  // mặc định hoàn thành 3 task


    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;




}
