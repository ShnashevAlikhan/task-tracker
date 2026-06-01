package com.temerlan.task_tracker.controller;

import com.temerlan.task_tracker.dto.ProjectRequest;
import com.temerlan.task_tracker.dto.ProjectResponse;
import com.temerlan.task_tracker.dto.ProjectUpdate;
import com.temerlan.task_tracker.service.ProjectService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {
        ProjectResponse body = projectService.create(request);

        log.info("Project with the name: {} created", body.title());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(body);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectUpdate update
            )
    {
        ProjectResponse body = projectService.updateProject(projectId, update);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }
    @GetMapping
    public List<ProjectResponse> findProjects() {
        return projectService.findAllProject();
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> findProject(@PathVariable Long projectId) {

        ProjectResponse body = projectService.findProject(projectId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }
}
