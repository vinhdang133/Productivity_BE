package com.productivity.web.dto.response;


import com.productivity.web.entity.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectReponse {
    private Long id;
    private String name;
    private String description;
    private ProjectStatus status;
    private int sortOrder;
    private int taskCountl;
    private LocalDate createdAt;
    private LocalDate updatedAt;

}
