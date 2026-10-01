package com.productivity.web.controller;


import com.productivity.web.dto.response.StreakResponse;
import com.productivity.web.dto.response.StreakSummaryResponse;
import com.productivity.web.entity.Account;
import com.productivity.web.entity.Streak;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.repository.StreakRepository;
import com.productivity.web.service.implement.StreakServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/streak")
@RequiredArgsConstructor
public class StreakController {
    private final StreakRepository streakRepository;
    private final StreakServiceImpl streakServiceImpl;
    private final AccountRepository accountRepository;

    @GetMapping("/current")
    public StreakResponse getCurrentStreak(Authentication authentication) {
        String email = authentication.getName();
        StreakResponse streakResponse = streakServiceImpl.getCurrentStreak(email);

        // Fetch the streak entity for the current date
        return streakServiceImpl.getCurrentStreak(email);

    }

    @PostMapping("/record-task")
    public ResponseEntity<String> recordTaskCompleted(Authentication authentication) {

        String email = authentication.getName();

        Account user = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));


        streakServiceImpl.recordTaskCompleted(user);
        return ResponseEntity.ok("Task recorded successfully");


    }


    @GetMapping("/todaystreak")
        public StreakResponse getTodayStreak (Authentication authentication){
            Account user = accountRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("Account not found"));

            streakServiceImpl.getTodayStreak(user.getEmail());
            return streakServiceImpl.getTodayStreak(user.getEmail());
        }

        @GetMapping("/summary")
        public StreakSummaryResponse getStreakSummary(Authentication authentication) {
            String email = authentication.getName();
            return streakServiceImpl.getStreakSummary(email);
        }
    }




