package com.temerlan.task_tracker.controller;


import com.temerlan.task_tracker.dto.*;
import com.temerlan.task_tracker.exception.BadRequestException;
import com.temerlan.task_tracker.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{projectId}/tasks/{taskId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long projectId,
                                                         @PathVariable Long taskId,
                                                         @Valid @RequestBody CommentRequest request) {
        CommentResponse body = commentService.createComment(projectId, taskId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(body);
    }

    @DeleteMapping("/{projectId}/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long projectId,
                                              @PathVariable Long taskId,
                                              @PathVariable Long commentId) {
        commentService.deleteComment(projectId, taskId, commentId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{projectId}/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long projectId,
                                                         @PathVariable Long taskId,
                                                         @PathVariable Long commentId,
                                                         @Valid @RequestBody CommentUpdate update) {
        CommentResponse body = commentService.updateComment(
                projectId,
                taskId,
                commentId,
                update);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @GetMapping("/{projectId}/tasks/{taskId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> findComment(@PathVariable Long projectId,
                                                       @PathVariable Long taskId,
                                                       @PathVariable Long commentId) {
        CommentResponse body = commentService.findComment(projectId, taskId, commentId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }

    @GetMapping("/{projectId}/tasks/{taskId}/comments")
    public ResponseEntity<PageResponse<CommentResponse>> findAllComments(@RequestParam(required = false, defaultValue = "1") int page,
                                                                         @PathVariable Long projectId,
                                                                         @PathVariable Long taskId,
                                                                         @ModelAttribute CommentFilter filter,
                                                                         @RequestParam(required = false, defaultValue = "CREATED_AT") CommentSortField sortBy,
                                                                         @RequestParam(required = false, defaultValue = "DESC") String direction) {
        int size = 10;
        if (page < 1) {
            throw new BadRequestException("Page cannot be negative");
        }

        int pageIndex = page - 1;

        Pageable pageRequest = PageRequest.of(pageIndex, size, Sort.Direction.fromString(direction), sortBy.getProperty());

        PageResponse<CommentResponse> body = commentService.findAllComments(projectId, taskId, filter, pageRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(body);
    }
}
