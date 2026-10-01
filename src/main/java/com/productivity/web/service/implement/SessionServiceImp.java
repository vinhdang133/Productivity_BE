package com.productivity.web.service.implement;

import com.productivity.web.dto.mapper.FocusSessionMapper;
import com.productivity.web.dto.request.FocusSessionRequest;
import com.productivity.web.dto.response.FocusSessionResponse;
import com.productivity.web.entity.Account;
import com.productivity.web.entity.FocusSession;
import com.productivity.web.entity.Label;
import com.productivity.web.entity.Task;
import com.productivity.web.entity.enums.SessionStatus;
import com.productivity.web.repository.*;
import com.productivity.web.service.SessionServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
@RequiredArgsConstructor
public class SessionServiceImp  implements SessionServiceInterface {


    private final AccountRepository accountRepository;
    private final FocusSessionRepository focusSessionRepository;
    private final TaskRepository taskRepository;
    private final LabelRepository labelRepository;
    private final StreakRepository streakRepository;

    @Override
    public FocusSessionResponse startSession(String email, FocusSessionRequest request) {
        // 1. Find current user
            Account account = accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));
        // 2. Check if user already has a RUNNING session

        // 3. Find Task if taskId != null

        // 4. Find Label if sessionLabelId != null

        // 5. Build FocusSession

        // 6. Save FocusSession

        // 7. Convert entity -> response
            FocusSession focusSession = focusSessionRepository.findByUserAndStatus(account, SessionStatus.RUNNING).orElse(null);
            if (focusSession != null) {
                throw new RuntimeException("You already have an active session");
            }

        // 3. Find Task if taskId is provided
        Task task = null;

        if (request.getTaskId() != null) {
            task = taskRepository
                    .findByIdAndUser(request.getTaskId(), account)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Task not found or not owned by user"
                            ));
        }
        // 4. Find Label if sessionLabelId is provided
        Label label = null;

        if (request.getSessionLabelId() != null) {
            label = labelRepository
                    .findByIdAndUser_Id(request.getSessionLabelId(), account.getId())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Label not found or not owned by user"
                            ));
        }


        FocusSession newSession = FocusSession.builder()
                    .user(account)
                    .task(task)
                    .sessionLabel(label)
                    .sessionType(request.getSessionType())
                    .plannedDurationMinutes(request.getPlannedDurationTimes())
                    .status(SessionStatus.RUNNING)
                    .startedAt(LocalDateTime.now())
                    .build();

            focusSessionRepository.save(newSession);


        return FocusSessionMapper.toResponse(newSession);
    }

    @Override
    public FocusSessionResponse finishSession(String email, Long sessionId) {
        //stopSession(email, sessionId)
        //│
        //├── 1. Find Account
        //│
        //├── 2. Find FocusSession by ID + Account
        //│
        //├── 3. Check status == RUNNING
        //│
        //├── 4. endedAt = LocalDateTime.now()
        //│
        //├── 5. Calculate actualDurationMinutes
        //│
        //├── 6. status = COMPLETED
        //│
        //├── 7. If Task exists
        //│      └── Task.addFocusMinutes(actualDurationMinutes)
        //│
        //├── 8. Record Streak
        //│
        //├── 9. Save FocusSession
        //│
        //└── 10. Return Mapper.toResponse(...)
        Account account = getUser(email);
        FocusSession focusSession = getFocusSession(email, sessionId);

        if (focusSession.getStatus() != SessionStatus.RUNNING) {
            throw new RuntimeException("Session is not running");
        }
          LocalDateTime Endednow = java.time.LocalDateTime.now();
          focusSession.setEndedAt(Endednow);

            LocalDateTime startedAt = focusSession.getStartedAt();


            //Actual duration in minutes

        long actualDuration = java.time.Duration.between(startedAt, Endednow).toMinutes();
        focusSession.setActualDurationMinutes((int) actualDuration);


        focusSession.setStatus(SessionStatus.COMPLETED);

        focusSessionRepository.save(focusSession);
        return FocusSessionMapper.toResponse(focusSession);




    }

    @Override
    public List<FocusSessionResponse> getMySessions(String email) {
        Account account = getUser(email);
        List<FocusSession> sessions = focusSessionRepository.findAllByUserOrderByStartedAtDesc(account);
        return sessions.stream().map(FocusSessionMapper::toResponse).toList();
    }



    @Override
    public FocusSessionResponse endSession(String email, Long sessionId) {
        return null;
    }

    @Override
    public FocusSessionResponse getActiveSession(String email) {
        Account account = getUser(email);
        FocusSession focusSession = focusSessionRepository.findByUserAndStatus(account, SessionStatus.RUNNING)
                .orElseThrow(() -> new RuntimeException("No active session found"));
        return FocusSessionMapper.toResponse(focusSession);
    }

    @Override
    public FocusSessionResponse getSession(String email, Long sessionId) {
        Account account = getUser(email);
        FocusSession focusSession = focusSessionRepository.findByIdAndUser(sessionId, account)
                .orElseThrow(() -> new RuntimeException("Focus session not found"));
        return FocusSessionMapper.toResponse(focusSession);
    }

    private Account getUser(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
    private FocusSession getFocusSession(String email, Long sessionId) {
        Account account = getUser(email);
        return focusSessionRepository.findByIdAndUser(sessionId, account)
                .orElseThrow(() -> new RuntimeException("Focus session not found"));
    }
}

