package com.productivity.web.service.implement;

import com.productivity.web.dto.request.CreateTaskRequest;
import com.productivity.web.dto.request.UpdateTaskRequest;
import com.productivity.web.dto.response.TaskResponse;
import com.productivity.web.entity.Account;
import com.productivity.web.entity.Project;
import com.productivity.web.entity.Task;
import com.productivity.web.entity.enums.Priority;
import com.productivity.web.entity.enums.TaskStatus;
import com.productivity.web.repository.AccountRepository;
import com.productivity.web.repository.ProjectRepository;
import com.productivity.web.repository.TaskRepository;
import com.productivity.web.service.TaskIServiceInterface;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class TaskServiceImp implements TaskIServiceInterface {

    private final TaskRepository taskRepository;
    private final AccountRepository accountRepository;
    private final ProjectRepository projectRepository;



    @Override
    public TaskResponse createTask(String email, CreateTaskRequest request) {
        Account user = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));


            Project project = null;
            if(request.getProjectId() != null){
                project = projectRepository.findByIdAndUser(request.getProjectId(), user)
                        .orElseThrow(() -> new RuntimeException("Project not found with id: " + request.getProjectId()));


            }

            Task task = Task.builder()
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .dueDate(request.getDueDate())
                    .priority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM)
                    .status(TaskStatus.TO_DO)
                    .user(user)
                    .project(project)
                    .build();

            Task savedTask = taskRepository.save(task);

            return mapToTaskResponse(savedTask);
    }



    @Override
    public List<TaskResponse> getMyTasks(String email) {
        Account user = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return taskRepository.findByUserOrderBySortOrderAscCreatedAtDesc(user)
                .stream()
                .map(this::mapToTaskResponse)
                .toList();
    }


    @Override
    public TaskResponse getTaskById(String email, Long taskId) {
        Account user = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        return mapToTaskResponse(task);
    }

    @Override
    public TaskResponse updateTask(String email, Long taskId, UpdateTaskRequest request) {
       Account user = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

       Task task = findTaskByIdAndUser(taskId, user);

       return mapToTaskResponse(taskRepository.save(task));


    }

    @Override
    public void deleteTask(String email, Long taskId) {

    }

    @Override
    public TaskResponse completeTask(String email, Long taskId) {
        return null;
    }

    @Override
    public TaskResponse reopenTask(String email, Long taskId) {
        return null;
    }

    @Override
    public List<TaskResponse> getMyTasksByStatus(String email, TaskStatus status) {
        return List.of();
    }

    @Override
    public List<TaskResponse> getTasksByPriority(String email, Priority priority) {
        return List.of();
    }

    @Override
    public List<TaskResponse> searchTasks(String email, String keyword) {
        return List.of();
    }

    private TaskResponse mapToTaskResponse(Task savedTask) {
        return TaskResponse.builder()
                .id(savedTask.getId())
                .title(savedTask.getTitle())
                .description(savedTask.getDescription())
                .status(savedTask.getStatus())
                .priority(savedTask.getPriority())
                .dueDate(savedTask.getDueDate())
                .overdue(savedTask.getDueDate() != null && savedTask.getDueDate().isBefore(java.time.LocalDateTime.now()))
                .sortOrder(savedTask.getSortOrder())
                .projectId(savedTask.getProject() != null ? savedTask.getProject().getId() : null)
                .projectName(savedTask.getProject() != null ? savedTask.getProject().getName() : null)
                .labels(savedTask.getLabels().stream().map(label -> label.getName()).toList())
                .completedAt(savedTask.getCompletedAt())
                .createdAt(savedTask.getCreatedAt())
                .updatedAt(savedTask.getUpdatedAt())
                .build();
    }
    private Task findTaskByIdAndUser(Long taskId, Account user) {
        return taskRepository.findByIdAndUser(taskId, user)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
    }
    private Project findProjectByIdAndUser(Long projectId, Account user) {
        return projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
    }

    private Account findAccountByEmail(String email) {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
