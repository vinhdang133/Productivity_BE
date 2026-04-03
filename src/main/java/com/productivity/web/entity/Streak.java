package com.productivity.web.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "streaks",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_streak_user_date",
                        columnNames = {"user_id", "streak_date"}   // mỗi ngày chỉ có 1 row
                )
        },
        indexes = {
                @Index(name = "idx_streaks_user_date", columnList = "user_id, streak_date")
        }
)
public class Streak {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(
                name = "user_id",
                nullable = false,
                foreignKey = @ForeignKey(name = "fk_streak_user")
        )
        private Account user;

        @Column(name = "streak_date", nullable = false)
        private LocalDate streakDate;  // chỉ lưu ngày, không lưu giờ


        @Column(name = "session_count", nullable = false)
        @Builder.Default
        private int sessionCount = 0;  // ngày đó đã có bao nhiêu focus

    @Column(name = "total_focus_minutes")
    @Builder.Default
    private int totalFocusMinutes = 0;  // tổng số phút focus trong ngày đó

    @Column(name = "task_completed")
    @Builder.Default
    private int tasksCompleted = 0;  // số task hoàn thành trong ngày đó

    @Column(name = "goal_met")
    @Builder.Default
    private boolean goalMet = false;  // có đạt mục tiêu ngày hay không




}
