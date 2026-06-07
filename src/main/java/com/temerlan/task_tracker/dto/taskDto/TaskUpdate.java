package com.temerlan.task_tracker.dto.taskDto;

import com.temerlan.task_tracker.entity.TaskPriority;
import com.temerlan.task_tracker.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskUpdate(
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime deadline
) {
}
