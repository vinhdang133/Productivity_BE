package com.productivity.web.service;

import com.productivity.web.dto.request.UpdateProjectRequest;
import com.productivity.web.dto.response.ProjectReponse;

import java.util.List;

public interface ProjectServiceInterface {

    //CREATE
    ProjectReponse createProject(Long userId, String name, String description);

    //READ
    ProjectReponse getProjectById(Long id, String email);
    List<ProjectReponse> getAllProjectsByUser(String email);
    List<ProjectReponse> getProjecsByStatus(String email, String status);

    //UPDATE
    ProjectReponse updateProject(Long id, UpdateProjectRequest email);

    //Delete
    void deleteProject(Long id, String email);
}
