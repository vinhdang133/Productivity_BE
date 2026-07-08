package com.productivity.web.controller;


import com.productivity.web.dto.request.CreateTaskRequest;
import com.productivity.web.dto.request.UpdateTaskRequest;
import com.productivity.web.dto.response.TaskResponse;
import com.productivity.web.service.implement.TaskServiceImp;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImp taskService;
    //CREATE

    @PostMapping("/create")
    public TaskResponse createTask(
            Authentication authentication,
            @Valid @RequestBody CreateTaskRequest createTaskRequest){
        String email = authentication.getName();
        return taskService.createTask(email, createTaskRequest);
    }

    //GET

    @GetMapping("/my-tasks")
    public List<TaskResponse> getMyTasks(Authentication authentication){
        String email = authentication.getName();
        return taskService.getMyTasks(email);
    }


    @GetMapping("/{taskId}")
    public TaskResponse getTask(@PathVariable("taskId")

                                    Authentication authentication,
                                    Long taskId){
        String email = authentication.getName();
        return taskService.getTaskById(email, taskId);
    }

    //Update
    public TaskResponse updateTask(Authentication authentication,
                                   @PathVariable("taskId") Long taskId,
                                   @Valid @RequestBody UpdateTaskRequest updateTaskRequest){
        String email = authentication.getName();
        return taskService.updateTask(email, taskId, updateTaskRequest);
    }

    //Delete
    public String deleteTask(
            Authentication authentication,
            @PathVariable("taskId") Long taskId){
        String email = authentication.getName();
        taskService.deleteTask(email, taskId);

        return "success";

    }

    //PatchMapping

    @PatchMapping("/{taskId}/complete")
    public TaskResponse completeTask(
            Authentication authentication,
            @PathVariable("taskId") Long taskId){
        String email = authentication.getName();
        return taskService.completeTask(email, taskId);
    }


    @PatchMapping("/{taskId}/reopen")
    public TaskResponse reopenTask(
            Authentication authentication,
            @PathVariable("taskId") Long taskId){
        String email = authentication.getName();
        return taskService.reopenTask(email, taskId);
    }
    
}
