package com.productivity.web.service.implement;


import com.productivity.web.entity.Account;
import com.productivity.web.entity.Streak;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.repository.StreakRepository;
import com.productivity.web.service.StreakServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StreakServiceImpl implements StreakServiceInterface {
    private final StreakRepository streakRepository;
    private final AccountRepository accountRepository;


    @Override
    public void recordTaskCompleted(Account user) {
            LocalDate today = LocalDate.now();

            Streak streak = streakRepository.findByUserOrderByStreakDate(user, today)
                    .orElseGet(()-> Streak.builder()
                            .user(user)
                            .streakDate(today)
                            .sessionCount(0)
                            .totalFocusMinutes(0)
                            .tasksCompleted(0)
                            .goalMet(false)
                            .build());

            streak.setTasksCompleted(streak.getTasksCompleted() + 1);

        // Rule đơn giản: hoàn thành ít nhất 1 task là đạt goal ngày
        if (streak.getTasksCompleted() >= 1) {
            streak.setGoalMet(true);
        }

        streakRepository.save(streak);


    }

    @Override
    public void recordFocusSession(Account user, int focusMinutes) {

    }

    @Override
    public int getCurrentStreak(String email) {
        return 0;
    }
}
