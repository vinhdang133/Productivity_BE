package com.productivity.web.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class StreakResponse {

    private Long id;

    private LocalDate streakDate;

    private Integer sessionCount;

    private Integer totalFocusMinutes;

    private boolean activeToday;

    private Integer currentStreak;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}