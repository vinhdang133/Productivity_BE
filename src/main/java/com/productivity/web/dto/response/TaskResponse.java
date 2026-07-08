package com.productivity.web.dto.response;


import com.productivity.web.entity.enums.Priority;
import com.productivity.web.entity.enums.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskResponse {

    private Long id;
    private String title;

    private String description;

    private TaskStatus status;
    private Priority priority;


    private Integer actualMinutes;

    private LocalDateTime dueDate;

    private boolean overdue;

    private Integer sortOrder;

    private Long projectId;

    private String projectName;

    private Long parentTaskId;

    private List<String> labels;

    private LocalDateTime completedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
