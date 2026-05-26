package com.productivity.web.controller;


import com.productivity.web.dto.request.CreateProjectRequest;
import com.productivity.web.dto.request.UpdateProjectRequest;
import com.productivity.web.dto.response.ProjectResponse;
import com.productivity.web.service.implement.ProjectServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectServiceImp projectService;


    //Create

    @PostMapping("/create")


    public ResponseEntity<ProjectResponse> createProject(
            Authentication authentication,
            @Valid @RequestBody CreateProjectRequest request) {
        String email = authentication.getName();
        ProjectResponse response = projectService.createProject(email, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //READ - GET BY ID
    @GetMapping("/getById/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(
    @PathVariable Long id,
        Authentication authentication){
        String email = authentication.getName();
        ProjectResponse project = projectService.getProjectById(id, email);
        return ResponseEntity.ok(project);
    }


    // READ - Get all projects
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects(
            Authentication authentication) {
        String email = authentication.getName();
        List<ProjectResponse> projects = projectService.getAllProjectsByUser(email);
        return ResponseEntity.ok(projects);
    }


    // READ - Get by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByStatus(
            @PathVariable String status,
            Authentication authentication) {
        String email = authentication.getName();
        List<ProjectResponse> projects = projectService.getProjecsByStatus(email, status);
        return ResponseEntity.ok(projects);
    }

    //UPDATE\
    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            Authentication authentication,
            @Valid @RequestBody UpdateProjectRequest request
    ){
        String email = authentication.getName();
        ProjectResponse response = projectService.updateProject(id, email, request);
        return ResponseEntity.ok(response);

    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(
            @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        projectService.deleteProject(id, email);
        return ResponseEntity.ok("Project deleted successfully");
    }

}
