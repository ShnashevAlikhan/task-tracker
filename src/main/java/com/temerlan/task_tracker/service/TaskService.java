package com.temerlan.task_tracker.service;

import com.temerlan.task_tracker.dto.TaskRequest;
import com.temerlan.task_tracker.dto.TaskResponse;
import com.temerlan.task_tracker.dto.TaskUpdate;
import com.temerlan.task_tracker.entity.Project;
import com.temerlan.task_tracker.entity.Task;
import com.temerlan.task_tracker.exception.ProjectNotFoundException;
import com.temerlan.task_tracker.mapper.TaskMapper;
import com.temerlan.task_tracker.repository.ProjectRepository;
import com.temerlan.task_tracker.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        Project project = findByIdAndUserIdOrThrow(projectId, userId);

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

    public List<TaskResponse> findAllTasksInProject(Long projectId) {
        Long userId = currentUserService.getCurrentUserId();

        findByIdAndUserIdOrThrow(projectId, userId);

        log.info("User viewed all tasks in project with id: {}", projectId);
        return taskRepository.findByProjectId(projectId)
                .stream()
                .map(taskMapper::toResponse)
                .toList();
    }

    public void deleteTaskInProject(Long projectId, Long taskId) {
        Long userId = currentUserService.getCurrentUserId();

        Project project = findByIdAndUserIdOrThrow(projectId, userId);

        project.removeTask(taskId);

        log.info("Task with id: {} removed in project id: {}", taskId, projectId);
    }

    public TaskResponse updateTaskInProject(Long projectId, Long taskId, TaskUpdate update) {
        Long userId = currentUserService.getCurrentUserId();

        Project project = findByIdAndUserIdOrThrow(projectId, userId);

        Task task = project.updateTask(
                taskId,
                update.title(),
                update.description(),
                update.status(),
                update.priority(),
                update.deadline()
        );

        log.info("Task with id: {} updated", taskId);
        return taskMapper.toResponse(task);
    }

    private Project findByIdAndUserIdOrThrow(Long projectId, Long userId) {
        return projectRepository.findByIdAndUserId(projectId, userId)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id: " + projectId + " not found"));
    }
}
