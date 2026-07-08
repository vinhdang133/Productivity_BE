package com.productivity.web.service;


import com.productivity.web.entity.Account;

public interface StreakServiceInterface {
    void recordTaskCompleted(Account user);

    void recordFocusSession(Account user, int focusMinutes);

    int getCurrentStreak(String email);
}
