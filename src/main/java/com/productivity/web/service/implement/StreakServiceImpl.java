package com.productivity.web.service.implement;


import com.productivity.web.dto.response.StreakResponse;
import com.productivity.web.dto.response.StreakSummaryResponse;
import com.productivity.web.entity.Account;
import com.productivity.web.entity.Streak;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.repository.StreakRepository;
import com.productivity.web.service.StreakServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StreakServiceImpl implements StreakServiceInterface {
    private final StreakRepository streakRepository;
    private final AccountRepository accountRepository;


    @Override
    public void recordTaskCompleted(Account user) {
            LocalDate today = LocalDate.now();

            Streak streak = streakRepository.findByUserOrderByStreakDate(user)
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
        LocalDate today = LocalDate.now();

        Streak streak = streakRepository.findByUserAndStreakDate(user, today)
                .orElseGet(()-> Streak.builder()
                        .user(user)
                        .streakDate(today)
                        .sessionCount(0)
                        .totalFocusMinutes(0)
                        .tasksCompleted(0)
                        .goalMet(false)
                        .build());

        streak.setSessionCount(streak.getSessionCount() + 1);
        streak.setTotalFocusMinutes(streak.getTotalFocusMinutes() + focusMinutes);

        if(streak.getTotalFocusMinutes() >= 25 || streak.getTasksCompleted() >= 1) {
            streak.setGoalMet(true);
        }
        streakRepository.save(streak);
    }

    @Override
    public StreakResponse getCurrentStreak(String email) {
        Account user = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

      List<Streak> streaks  = streakRepository.findByUserOrderByStreakDateDesc(user);
    if(streaks.isEmpty()) {
        return StreakResponse.builder()
                .id(0L)
                .streakDate(null)
                .sessionCount(0)
                .totalFocusMinutes(0)
                .activeToday(false)
                .build();
    }
    LocalDate today = LocalDate.now();
    LocalDate latestDate = streaks.get(0).getStreakDate();

    LocalDate checkingDate;

    if(latestDate.isEqual(today)) {
        checkingDate = today;

    }else if(latestDate.isEqual(today.minusDays(1))) {
        checkingDate = latestDate;

    }else{
        return StreakResponse.builder()
                .id(0L)
                .streakDate(null)
                .sessionCount(0)
                .totalFocusMinutes(0)
                .activeToday(false)
                .build();
    }
    int currentStreak = 0;

    for(Streak streak : streaks) {
        if(streak.getStreakDate().isEqual(checkingDate) && streak.isGoalMet()){
            currentStreak++;
            checkingDate = checkingDate.minusDays(1);
        }else{
                break;
        }
    }


        return StreakResponse.builder()
                .id(0L)
                .streakDate(null)
                .sessionCount(0)
                .totalFocusMinutes(0)
                .activeToday(false)
                .build();
    }

    @Override
    public StreakResponse getTodayStreak(String email) {
        Account user = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Streak todayStreak = streakRepository.findByUserOrderByStreakDate(user)
                .orElseGet(()-> Streak.builder()
                        .user(user)
                        .streakDate(LocalDate.now())
                        .sessionCount(0)
                        .totalFocusMinutes(0)
                        .tasksCompleted(0)
                        .goalMet(false)
                        .build());
        return StreakResponse.builder()
                .id(todayStreak.getId())
                .streakDate(todayStreak.getStreakDate())
                .sessionCount(todayStreak.getSessionCount())
                .totalFocusMinutes(todayStreak.getTotalFocusMinutes())
                .activeToday(todayStreak.isGoalMet())
                .build();
    }

    @Override
    public StreakSummaryResponse getStreakSummary(String email) {
        Account user = getUser(email);

        List<Streak> streaks = streakRepository.findByUserOrderByStreakDateDesc(user);

        int currentStreak = calculateCurrentStreak(streaks);
        int longestStreak = calculateLongestStreak(streaks);
        int totalActiveDays = calculateTotalActiveDays(streaks);
        int totalFocusMinutes = calculateTotalFocusMinutes(streaks);
        LocalDate lastActiveDate = findLastActiveDate(streaks);

        return StreakSummaryResponse.builder()
                .currentStreak(currentStreak)
                .longestStreak(longestStreak)
                .totalActiveDays(totalActiveDays)
                .totalFocusMinutes(totalFocusMinutes)
                .lastActiveDate(lastActiveDate)
                .build();
    }

    private Account getUser(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    private StreakSummaryResponse emptyStreakSummary() {
        return StreakSummaryResponse.builder()
                .currentStreak(0)
                .longestStreak(0)
                .totalActiveDays(0)
                .totalFocusMinutes(0)
                .lastActiveDate(null)
                .build();
    }

    //Hàm tính chuỗi hiện tại
    //check chuỗi từ mới nhất -> cũ
    //khi goatmet (trong entity streak) thi curent se cong 1

    private int calculateCurrentStreak(List<Streak> streak) {
        int currentStreak = 0;
        LocalDate checkingDate  = LocalDate.now();

        for (Streak streak1 : streak) {
            if (streak1.getStreakDate().isEqual(checkingDate)) {
                currentStreak++;
                checkingDate = checkingDate.minusDays(1);

            }else{
                break;
            }
        }
            return  currentStreak;
    }
        private int calculateLongestStreak(List<Streak> streaks) {
            int longestStreak = 0;
            int tempStreak = 0;
            LocalDate expectedDate = null;

            for (Streak streak1 : streaks) {
                if (streak1.isGoalMet()) {
                    tempStreak = 0;
                    expectedDate = null;
                    continue;

                }
                if (expectedDate == null) {
                    tempStreak = 1;
                } else if (streak1.getStreakDate().isEqual(expectedDate)) {
                    tempStreak++;
                } else {
                    tempStreak = 1;
                }
                longestStreak = Math.max(longestStreak, tempStreak);
                expectedDate = streak1.getStreakDate().minusDays(1);

            }
            return longestStreak;

        }
    private int calculateTotalFocusMinutes(List<Streak> streaks) {
        int total = 0;

        for (Streak streak : streaks) {
            total += streak.getTotalFocusMinutes();
        }

        return total;
    }
    private LocalDate findLastActiveDate(List<Streak> streaks) {
        return streaks.stream()
                .filter(Streak::isGoalMet)
                .map(Streak::getStreakDate)
                .max(LocalDate::compareTo)
                .orElse(null);
    }
    private int calculateTotalActiveDays(List<Streak> streaks) {
        return (int) streaks.stream()
                .filter(Streak::isGoalMet)
                .count();
    }

}
