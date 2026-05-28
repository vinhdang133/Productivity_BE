package com.productivity.web.dto.request;

import com.productivity.web.entity.enums.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CreateTaskRequest {
    @NotBlank(message = "Task title is required")
    private String title;

    private String description;

    private Priority priority;
    private Integer estimatedMinutes;

    private LocalDateTime dueDate;

    private Long projectId;
    private Integer sortOrder;

    private List<Long> labelId;


}
