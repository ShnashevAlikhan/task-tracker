package com.temerlan.task_tracker.service;

import com.temerlan.task_tracker.dto.*;
import com.temerlan.task_tracker.dto.taskDto.TaskFilter;
import com.temerlan.task_tracker.dto.taskDto.TaskRequest;
import com.temerlan.task_tracker.dto.taskDto.TaskResponse;
import com.temerlan.task_tracker.dto.taskDto.TaskUpdate;
import com.temerlan.task_tracker.entity.Project;
import com.temerlan.task_tracker.entity.Task;
import com.temerlan.task_tracker.exception.ProjectNotFoundException;
import com.temerlan.task_tracker.exception.TaskNotFoundException;
import com.temerlan.task_tracker.mapper.TaskMapper;
import com.temerlan.task_tracker.repository.ProjectRepository;
import com.temerlan.task_tracker.repository.TaskRepository;
import com.temerlan.task_tracker.specification.TaskSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectRepository projectRepository;
    private final CurrentUserService currentUserService;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, ProjectRepository projectRepository, CurrentUserService currentUserService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectRepository = projectRepository;
        this.currentUserService = currentUserService;
    }

    public TaskResponse createTaskInProject(Long projectId, TaskRequest request) {
        Long userId = currentUserService.getCurrentUserId();

        Project project = findProjectByIdAndUserIdOrThrow(projectId, userId);

        Task task = Task.create(
                request.title(),
                request.description(),
                request.status(),
                request.priority(),
                request.deadline()
        );

        project.attachTask(task);

        taskRepository.save(task);

        log.info("Task with id: {} saved in project with id: {}", task.getId(), project.getId());
        return taskMapper.toResponse(task);
    }

    public TaskResponse findTaskInProject(Long projectId, Long taskId) {
        Long userId = currentUserService.getCurrentUserId();

        Task task = findByIdAndProject_IdAndProject_User_Id(
                taskId,
                projectId,
                userId
        );

        log.info("Task with id: {} viewed in project with id: {}", taskId, projectId);
        return taskMapper.toResponse(task);
    }
    public PageResponse<TaskResponse> findAllTasksInProject(Long projectId, TaskFilter filter, Pageable page) {
        Long userId = currentUserService.getCurrentUserId();

        findProjectByIdAndUserIdOrThrow(projectId, userId);

        log.info("User viewed all tasks in project with id: {}", projectId);

        Page<Task> pages = taskRepository.findAll(TaskSpecification.filter(projectId, userId, filter), page);

        return PageResponse.from(pages, taskMapper::toResponse);
    }

    public void deleteTaskInProject(Long projectId, Long taskId) {
        Long userId = currentUserService.getCurrentUserId();

        Project project = findProjectByIdAndUserIdOrThrow(projectId, userId);

        Task task = findByIdAndProject_IdAndProject_User_Id(
                taskId,
                projectId,
                userId
        );

        project.removeTask(task);

        log.info("Task with id: {} removed in project id: {}", taskId, projectId);
    }

    public TaskResponse updateTaskInProject(Long projectId, Long taskId, TaskUpdate update) {
        Long userId = currentUserService.getCurrentUserId();

        Task task = findByIdAndProject_IdAndProject_User_Id(
                taskId,
                projectId,
                userId
        );

        task.updateTask(
                update.title(),
                update.description(),
                update.status(),
                update.priority(),
                update.deadline()
        );

        log.info("Task with id: {} updated", taskId);
        return taskMapper.toResponse(task);
    }

    private Project findProjectByIdAndUserIdOrThrow(Long projectId, Long userId) {
        return projectRepository.findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id: " + projectId + " not found"));
    }

    private Task findByIdAndProject_IdAndProject_User_Id(
            Long taskId,
            Long projectId,
            Long userId)
    {
        return taskRepository.findByIdAndProject_IdAndProject_User_Id(
                taskId,
                projectId,
                userId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: " + taskId + " not found"));
    }
}
