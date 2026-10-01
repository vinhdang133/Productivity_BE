package com.productivity.web.service;


import com.productivity.web.dto.request.FocusSessionRequest;
import com.productivity.web.dto.response.FocusSessionResponse;

import java.util.List;

public interface SessionServiceInterface {

    FocusSessionResponse startSession(String email, FocusSessionRequest request);

    FocusSessionResponse finishSession(String email, Long sessionId);

    List<FocusSessionResponse> getMySessions(String email);



    FocusSessionResponse endSession(String email, Long sessionId);

    FocusSessionResponse getSession(
            String email,
            Long sessionId
    );
    FocusSessionResponse getActiveSession(
            String email
    );
}
