package com.temerlan.task_tracker.service;

import com.temerlan.task_tracker.dto.PageResponse;
import com.temerlan.task_tracker.dto.projectDto.ProjectFilter;
import com.temerlan.task_tracker.dto.projectDto.ProjectRequest;
import com.temerlan.task_tracker.dto.projectDto.ProjectResponse;
import com.temerlan.task_tracker.dto.projectDto.ProjectUpdate;
import com.temerlan.task_tracker.entity.Project;
import com.temerlan.task_tracker.entity.User;
import com.temerlan.task_tracker.exception.ProjectNotFoundException;
import com.temerlan.task_tracker.exception.UserNotFoundException;
import com.temerlan.task_tracker.mapper.ProjectMapper;
import com.temerlan.task_tracker.repository.ProjectRepository;
import com.temerlan.task_tracker.repository.UserRepository;
import com.temerlan.task_tracker.specification.ProjectSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CurrentUserService currentUserService;

    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, CurrentUserService currentUserService, ProjectMapper projectMapper, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.currentUserService = currentUserService;
        this.projectMapper = projectMapper;
        this.userRepository = userRepository;
    }

    public ProjectResponse create(ProjectRequest request) {
        Long currentUserId = currentUserService.getCurrentUserId();

        User user = findUserByIdOrThrow(currentUserId);

        Project project = Project.create(
                request.title(),
                request.description()
        );

        user.attachProject(project);

        projectRepository.save(project);

        log.info("Project with id: {}, attached to user with id: {}",
                project.getId(),
                user.getId());

        return projectMapper.toResponse(project);
    }

    public ProjectResponse updateProject(Long projectId, ProjectUpdate update) {
        Long currentUserId = currentUserService.getCurrentUserId();

        Project project = findByIdAndUserId(projectId, currentUserId);

        project.updateProject(
                update.title(),
                update.description()
        );

        log.info("Project with id: {} updated", project.getId());

        return projectMapper.toResponse(project);
    }
    public void deleteProject(Long projectId) {
        Long userId = currentUserService.getCurrentUserId();

        User user = findUserByIdOrThrow(userId);

        Project project = findByIdAndUserId(projectId, userId);

        user.removeProject(project);

        log.info("User with id: {} deleted project with id: {}", userId, projectId);

    }
    public PageResponse<ProjectResponse> findAllProject(ProjectFilter filter, Pageable page) {
        Long userId = currentUserService.getCurrentUserId();

        log.info("The user with id: {} viewed his project", userId);

        Page<Project> pages = projectRepository.findAll(ProjectSpecification.filter(userId, filter), page);

        return new PageResponse(
                pages.getContent().stream().map(projectMapper::toResponse).toList(),
                pages.getNumber(),
                pages.getSize(),
                pages.getTotalElements(),
                pages.getTotalPages(),
                pages.isFirst(),
                pages.isLast()
        );
    }

    public ProjectResponse findProject(Long projectId) {
        Long userId = currentUserService.getCurrentUserId();

        Project project = findByIdAndUserId(projectId, userId);

        log.info("User with id: {} reviewed his projects", userId);

        return projectMapper.toResponse(project);
    }
    private User findUserByIdOrThrow(Long userId) {
        if(userId == null) throw new IllegalArgumentException("User id cannot be null");

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " not found"));
    }

    private Project findByIdAndUserId(Long projectId, Long userId) {
        if(projectId == null) throw new IllegalArgumentException("Project id cannot be null");

        if(userId == null) throw new IllegalArgumentException("User id cannot be null");

        return projectRepository.findByIdAndUserId(projectId, userId)
                    .orElseThrow(() -> new ProjectNotFoundException("Project with id: " + projectId + " not found"));
    }
}
