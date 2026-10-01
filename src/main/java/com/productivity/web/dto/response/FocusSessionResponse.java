package com.productivity.web.dto.response;

import com.productivity.web.entity.enums.SessionStatus;
import com.productivity.web.entity.enums.SessionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FocusSessionResponse {

    private Long id;

    private Long taskId;

    private Long sessionLabelId;

    private SessionType sessionType;

    private SessionStatus status;

    private Integer plannedDurationMinutes;

    private Integer actualDurationMinutes;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private String notes;
}