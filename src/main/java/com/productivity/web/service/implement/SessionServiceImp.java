package com.productivity.web.service.implement;

import com.productivity.web.dto.request.FocusSessionRequest;
import com.productivity.web.dto.response.FocusSessionResponse;
import com.productivity.web.entity.Account;
import com.productivity.web.entity.FocusSession;
import com.productivity.web.entity.Task;
import com.productivity.web.entity.enums.SessionStatus;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.repository.FocusSessionRepository;
import com.productivity.web.repository.TaskRepository;
import com.productivity.web.service.SessionServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImp  implements SessionServiceInterface {


    private final AccountRepository accountRepository;
    private final FocusSessionRepository focusSessionRepository;
    private final TaskRepository taskRepository;

    @Override
    public FocusSessionResponse startSession(String email, FocusSessionRequest request) {
            Account account = accountRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Account not found"));

            FocusSession focusSession = focusSessionRepository.findByUserAndStatus(account, SessionStatus.RUNNING).orElse(null);
            if (focusSession != null) {
                throw new RuntimeException("You already have an active session");
            }

            Task findTask = taskRepository.findById(request.getTaskId()).orElseThrow(() -> new RuntimeException("Task not found"));
            //find task by id and check if it belongs to the user


           if (findTask != null) {
               

            }
        return null;
    }

    @Override
    public FocusSessionResponse stopSession(String email, Long sessionId) {
        return null;
    }

    @Override
    public List<FocusSessionResponse> getMySessions(String email) {
        return List.of();
    }

    @Override
    public FocusSessionResponse finishSession(String email, Long sessionId) {
        return null;
    }

    @Override
    public FocusSessionResponse endSession(String email, Long sessionId) {
        return null;
    }

    private Account getUser(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
}
