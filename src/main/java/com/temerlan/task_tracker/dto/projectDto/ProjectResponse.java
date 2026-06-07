package com.temerlan.task_tracker.dto.projectDto;

import java.time.LocalDateTime;

public record ProjectResponse(
        Long id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {
}
