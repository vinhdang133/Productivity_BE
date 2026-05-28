package com.productivity.web.dto.request;

import com.productivity.web.entity.enums.Priority;
import com.productivity.web.entity.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UpdateTaskRequest {
    private String title;

    private String description;

    private TaskStatus status;

    private Priority priority;

    private Integer estimatedMinutes;

    private LocalDateTime dueDate;

    private Long projectId;

    private Integer sortOrder;

    private List<Long> labelIds;

}
