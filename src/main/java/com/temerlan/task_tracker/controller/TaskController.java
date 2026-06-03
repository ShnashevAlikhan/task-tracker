package com.temerlan.task_tracker.controller;

import com.temerlan.task_tracker.dto.TaskRequest;
import com.temerlan.task_tracker.dto.TaskResponse;
import com.temerlan.task_tracker.dto.TaskUpdate;
import com.temerlan.task_tracker.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createTask(@PathVariable Long projectId,
                                                   @Valid @RequestBody TaskRequest request) {
        TaskResponse body = taskService.createTaskInProject(projectId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);
    }

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<List<TaskResponse>> findAllTasksInProject(@PathVariable Long projectId) {
        List<TaskResponse> body = taskService.findAllTasksInProject(projectId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @GetMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskResponse> findTaskInProject(@PathVariable Long projectId,
                                                          @PathVariable Long taskId) {
        TaskResponse body = taskService.findTaskInProject(projectId, taskId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTaskInProject(@PathVariable Long projectId,
                                                    @PathVariable Long taskId) {
        taskService.deleteTaskInProject(projectId, taskId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{projectId}/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTaskInProject(@PathVariable Long projectId,
                                                            @PathVariable Long taskId,
                                                            @RequestBody TaskUpdate update) {
        TaskResponse body = taskService.updateTaskInProject(projectId, taskId, update);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

}
