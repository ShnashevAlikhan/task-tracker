package com.temerlan.task_tracker.controller;

import com.temerlan.task_tracker.dto.*;
import com.temerlan.task_tracker.dto.projectDto.*;
import com.temerlan.task_tracker.exception.BadRequestException;
import com.temerlan.task_tracker.service.ProjectService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

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
    public ResponseEntity<PageResponse<ProjectResponse>> findProjects(@RequestParam(required = false, defaultValue = "1") int page,
                                                                      @ModelAttribute ProjectFilter filter,
                                                                      @RequestParam(required = false, defaultValue = "CREATED_AT") ProjectSortField sortBy,
                                                                      @RequestParam(required = false, defaultValue = "ASC") String direction) {

        int size = 10;
        if (page < 1) {
            throw new BadRequestException("Page cannot be negative");
        }

        int pageIndex = page - 1;

        Pageable pageRequest = PageRequest.of(pageIndex, size, Sort.Direction.fromString(direction), sortBy.getProperty());

        PageResponse<ProjectResponse> body = projectService.findAllProject(filter, pageRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> findProject(@PathVariable Long projectId) {

        ProjectResponse body = projectService.findProject(projectId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }
}
