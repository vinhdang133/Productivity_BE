package com.productivity.web.service;


import com.productivity.web.dto.response.StreakResponse;
import com.productivity.web.dto.response.StreakSummaryResponse;
import com.productivity.web.entity.Account;

public interface StreakServiceInterface {
    void recordTaskCompleted(Account user);

    void recordFocusSession(Account user, int focusMinutes);

    StreakResponse getCurrentStreak(String email);

    StreakResponse getTodayStreak(String email);
    StreakSummaryResponse getStreakSummary(String email);

}
