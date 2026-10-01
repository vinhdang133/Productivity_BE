package com.productivity.web.controller;

import com.productivity.web.dto.request.FocusSessionRequest;
import com.productivity.web.dto.response.FocusSessionResponse;
import com.productivity.web.service.SessionServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionServiceInterface sessionService;

    @PostMapping("/start")
    public ResponseEntity<FocusSessionResponse> startSession(
            @RequestBody FocusSessionRequest request,
            Authentication authentication
    ) {
        String email = authentication.getName();

        FocusSessionResponse response =
                sessionService.startSession(email, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FocusSessionResponse>> getMySessions(
            Authentication authentication
    ) {
        String email = authentication.getName();

        List<FocusSessionResponse> response =
                sessionService.getMySessions(email);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{sessionId}/end")
    public ResponseEntity<FocusSessionResponse> endSession(
            @PathVariable Long sessionId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        FocusSessionResponse response =
                sessionService.endSession(email, sessionId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<FocusSessionResponse> getActiveSession(
            Authentication authentication
    ) {
        String email = authentication.getName();

        FocusSessionResponse response =
                sessionService.getActiveSession(email);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<FocusSessionResponse> getSession(
            @PathVariable Long sessionId,
            Authentication authentication
    ) {
        String email = authentication.getName();

        FocusSessionResponse response =
                sessionService.getSession(email, sessionId);

        return ResponseEntity.ok(response);
    }
}