package com.productivity.web.dto.request;

import com.productivity.web.entity.Label;
import com.productivity.web.entity.Task;
import com.productivity.web.entity.enums.SessionType;
import io.jsonwebtoken.security.Request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.aspectj.apache.bcel.classfile.Module;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Builder
public class FocusSessionRequest {

    private Long taskId;

    private Long sessionLabelId;

    @NotNull(message = "Session type is required")
    private SessionType sessionType;

    @NotNull
    @Min(1)
    private Integer plannedDurationTimes;
}
