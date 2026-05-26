package com.productivity.web.dto.response;


import com.productivity.web.entity.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private int sortOrder;
    private int taskCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
