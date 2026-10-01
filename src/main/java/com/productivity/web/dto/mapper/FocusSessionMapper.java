package com.productivity.web.dto.mapper;

import com.productivity.web.dto.response.FocusSessionResponse;
import com.productivity.web.entity.FocusSession;

public class FocusSessionMapper {
    private FocusSessionMapper() {
        // Prevent instantiation
    }
    public static FocusSessionResponse toResponse(FocusSession session) {

        if (session == null) {
            return null;
        }

        return FocusSessionResponse.builder()
                .id(session.getId())
                .taskId(
                        session.getTask() != null
                                ? session.getTask().getId()
                                : null
                )
                .sessionLabelId(
                        session.getSessionLabel() != null
                                ? session.getSessionLabel().getId()
                                : null
                )
                .sessionType(session.getSessionType())
                .status(session.getStatus())
                .plannedDurationMinutes(
                        session.getPlannedDurationMinutes()
                )
                .actualDurationMinutes(
                        session.getActualDurationMinutes()
                )
                .startedAt(session.getStartedAt())
                .endedAt(session.getEndedAt())
                .notes(session.getNotes())
                .build();
    }
}
