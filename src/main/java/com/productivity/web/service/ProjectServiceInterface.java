package com.productivity.web.service;

import com.productivity.web.dto.request.CreateProjectRequest;
import com.productivity.web.dto.request.UpdateProjectRequest;
import com.productivity.web.dto.response.ProjectResponse;

import java.util.List;

public interface ProjectServiceInterface {

    //CREATE
    ProjectResponse createProject(String email, CreateProjectRequest request);

    //READ
    ProjectResponse getProjectById(Long id, String email);
    List<ProjectResponse> getAllProjectsByUser(String email);
    List<ProjectResponse> getProjecsByStatus(String email, String status);

    //UPDATE
    ProjectResponse updateProject(Long id, String email, UpdateProjectRequest request);

    //Delete
    void deleteProject(Long id, String email);
}
