package com.temerlan.task_tracker.dto;

import com.temerlan.task_tracker.entity.TaskPriority;
import com.temerlan.task_tracker.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime deadline
) {
}
