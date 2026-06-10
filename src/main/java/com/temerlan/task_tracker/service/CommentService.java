package com.temerlan.task_tracker.service;

import com.temerlan.task_tracker.dto.*;
import com.temerlan.task_tracker.entity.Comment;
import com.temerlan.task_tracker.entity.Task;
import com.temerlan.task_tracker.exception.CommentNotFoundException;
import com.temerlan.task_tracker.exception.TaskNotFoundException;
import com.temerlan.task_tracker.mapper.CommentMapper;
import com.temerlan.task_tracker.repository.CommentRepository;
import com.temerlan.task_tracker.repository.TaskRepository;
import com.temerlan.task_tracker.specification.CommentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CurrentUserService currentUserService;
    private final TaskRepository taskRepository;
    private final CommentMapper commentMapper;

    public CommentResponse createComment(Long projectId,
                                         Long taskId,
                                         CommentRequest request) {
        Long userId = currentUserService.getCurrentUserId();

        Task task = findOwnedTaskOrThrow(
                taskId,
                projectId,
                userId);

        Comment comment = Comment.create(request.description());

        task.attachComment(comment);

        Comment savedComment = commentRepository.save(comment);

        log.info("Comment with id: {} was created and attached to a task with id: {}", savedComment.getId(), task.getId());
        return commentMapper.toResponse(savedComment);
    }

    public void deleteComment(Long projectId,
                              Long taskId,
                              Long commentId) {
        Long userId = currentUserService.getCurrentUserId();

        Task task = findOwnedTaskOrThrow(
              taskId,
              projectId,
              userId
        );

        Comment comment = findCommentInTaskOrThrow(commentId, taskId);

        task.removeComment(comment);

        log.info("Comment with id: {} deleted", comment.getId());
    }

    public CommentResponse updateComment(Long projectId,
                                         Long taskId,
                                         Long commentId,
                                         CommentUpdate update) {
        Long userId = currentUserService.getCurrentUserId();


        Task task = findOwnedTaskOrThrow(
                taskId,
                projectId,
                userId
        );

        Comment comment = findCommentInTaskOrThrow(commentId, taskId);

        comment.update(update.description());

        return commentMapper.toResponse(comment);
    }

    public CommentResponse findComment(Long projectId,
                                       Long taskId,
                                       Long commentId) {
        Long userId = currentUserService.getCurrentUserId();

        findOwnedTaskOrThrow(
                taskId,
                projectId,
                userId
        );

        Comment comment = findCommentInTaskOrThrow(commentId, taskId);

        return commentMapper.toResponse(comment);
    }

    public PageResponse<CommentResponse> findAllComments(Long projectId, Long taskId, CommentFilter filter, Pageable page) {
        Long userId = currentUserService.getCurrentUserId();

        findOwnedTaskOrThrow(taskId, projectId, userId);

        Page<Comment> pages = commentRepository.findAll(CommentSpecification.filter(projectId, taskId, filter), page);

        return new PageResponse<>(
                pages.getContent().stream().map(commentMapper::toResponse).toList(),
                pages.getNumber(),
                pages.getSize(),
                pages.getTotalElements(),
                pages.getTotalPages(),
                pages.isFirst(),
                pages.isLast()
        );
    }

    private Task findOwnedTaskOrThrow(
            Long taskId,
            Long projectId,
            Long userId
    ) {
        return taskRepository.findByIdAndProject_IdAndProject_User_Id(
                taskId,
                projectId,
                userId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: " + taskId + " not found"));
    }

    private Comment findCommentInTaskOrThrow(
            Long commentId,
            Long taskId
    ) {
        return commentRepository
                .findByIdAndTask_Id(commentId, taskId)
                .orElseThrow(() ->
                        new CommentNotFoundException("Comment not found"));
    }
}
