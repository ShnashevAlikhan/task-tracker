package com.temerlan.task_tracker.dto;

import java.time.LocalDateTime;

public record CommentResponse(
        Long id,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
