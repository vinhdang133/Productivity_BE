package com.productivity.web.service.implement;


import com.productivity.web.dto.request.CreateProjectRequest;
import com.productivity.web.dto.request.UpdateProjectRequest;
import com.productivity.web.dto.response.ProjectResponse;
import com.productivity.web.entity.Account;
import com.productivity.web.entity.Project;
import com.productivity.web.entity.enums.ProjectStatus;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.repository.ProjectRepository;
import com.productivity.web.service.ProjectServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImp implements ProjectServiceInterface {

    private final ProjectRepository projectRepository;
    private final AccountRepository accountRepository;


    private Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Account not found with email: " + email));
    }
    private Project findProjectByIdAndUser(Long projectId, Account user) {
        return projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
    }


    @Override
    public ProjectResponse createProject(String email, CreateProjectRequest request) {
        Account user = findAccountByEmail(email);


        Project project = Project.builder()
                .name(request.getName())
                .description(request.getDescription())
                .user(user)
                .status(ProjectStatus.ACTIVE)
                .sortOrder(0)
                .build();

        Project savedProject = projectRepository.save(project);
        return mapToProjectResponse(savedProject);
    }

    @Override
    public ProjectResponse getProjectById(Long id, String email) {
        Account user = findAccountByEmail(email);
        Project project = findProjectByIdAndUser(id, user);
        return mapToProjectResponse(project);
    }

    @Override
    public List<ProjectResponse> getAllProjectsByUser(String email) {
         Account user = findAccountByEmail(email);
            return projectRepository.findByUser(user)
                    .stream()
                    .map(this::mapToProjectResponse)
                    .toList();
    }

    @Override
    public List<ProjectResponse> getProjecsByStatus(String email, String status) {
          Account user = findAccountByEmail(email);
        ProjectStatus projectStatus= ProjectStatus.valueOf(status.toUpperCase());

        return projectRepository.findByUserAndStatus(user, projectStatus)
                .stream()
                .map(this::mapToProjectResponse)
                .toList();
    }

    @Override
    public ProjectResponse updateProject(Long id, String email, UpdateProjectRequest request) {
        Account user = findAccountByEmail(email);
          Project project = findProjectByIdAndUser(id, user);

    if (request.getName() != null && !request.getName().isBlank()) {
            project.setName(request.getName());
    }

    if (request.getDescription() != null && !request.getDescription().isBlank()) {
            project.setDescription(request.getDescription());

    }

    if(request.getStatus() != null){
    project.setStatus(request.getStatus());
    }

    project.setSortOrder(request.getSortOrder());


        Project updatedProject = projectRepository.save(project);
        return mapToProjectResponse(updatedProject);

    }

    @Override
    public void deleteProject(Long id, String email) {
        Account user = findAccountByEmail(email);
        Project project = findProjectByIdAndUser(id, user);
        projectRepository.delete(project);
    }

    private ProjectResponse mapToProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .status(project.getStatus())
                .sortOrder(project.getSortOrder())
                .taskCount(project.getTasks().size())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}
