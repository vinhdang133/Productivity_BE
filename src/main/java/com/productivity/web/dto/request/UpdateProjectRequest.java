package com.productivity.web.dto.request;


import com.productivity.web.entity.enums.ProjectStatus;
import lombok.Data;

@Data
public class UpdateProjectRequest {

    private String name;
    private String description;
    private ProjectStatus status;
    private int sortOrder;

}
