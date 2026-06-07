package com.temerlan.task_tracker.controller;

import com.temerlan.task_tracker.dto.*;
import com.temerlan.task_tracker.dto.taskDto.*;
import com.temerlan.task_tracker.exception.BadRequestException;
import com.temerlan.task_tracker.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<PageResponse<TaskResponse>> findAllTasksInProject(
            @PathVariable Long projectId,
            @ModelAttribute TaskFilter filter,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "ID") TaskSortField sortBy,
            @RequestParam(required = false, defaultValue = "DESC") String direction)
    {
        int size = 10;
        if (page < 1) {
            throw new BadRequestException("Page cannot be negative");
        }

        int pageIndex = page - 1;


        Pageable requestPages = PageRequest.of(pageIndex, size, Sort.by(Sort.Direction.fromString(direction), sortBy.getProperty()));

        PageResponse<TaskResponse> body = taskService.findAllTasksInProject(projectId, filter, requestPages);

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
