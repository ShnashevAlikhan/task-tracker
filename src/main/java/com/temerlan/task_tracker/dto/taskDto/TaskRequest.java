package com.temerlan.task_tracker.dto.taskDto;

import com.temerlan.task_tracker.entity.TaskPriority;
import com.temerlan.task_tracker.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskRequest(
        @NotBlank
        @Size(max = 255, message = "Title is too long")
        String title,
        @Size(max = 255, message = "Description is too long")
        String description,

        @NotNull
        TaskStatus status,
        @NotNull
        TaskPriority priority,
        LocalDateTime deadline

) {
}
