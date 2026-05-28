package com.productivity.web.service;


import com.productivity.web.dto.request.CreateTaskRequest;
import com.productivity.web.dto.request.UpdateTaskRequest;
import com.productivity.web.dto.response.TaskResponse;
import com.productivity.web.entity.enums.Priority;
import com.productivity.web.entity.enums.TaskStatus;

import java.util.List;

public interface TaskIServiceInterface {
    TaskResponse createTask(String email, CreateTaskRequest request);

    List<TaskResponse> getMyTasks(String email);

    TaskResponse getTaskById(String email,Long taskId);

    TaskResponse updateTask(String email, Long taskId, UpdateTaskRequest request);

    void deleteTask(String email,Long taskId);

    TaskResponse completeTask(String email,Long taskId);

    TaskResponse reopenTask(String email,Long taskId);

     List<TaskResponse> getMyTasksByStatus(String email, TaskStatus status);

     List<TaskResponse> getTasksByPriority(String email, Priority priority);

     List<TaskResponse> searchTasks(String email, String keyword);
}
