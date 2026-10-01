package com.productivity.web.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class StreakSummaryResponse {

    private Integer currentStreak;

    private Integer longestStreak;

    private Integer totalActiveDays;

    private Integer totalFocusMinutes;

    private LocalDate lastActiveDate;
}