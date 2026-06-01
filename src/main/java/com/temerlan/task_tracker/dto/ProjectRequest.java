package com.temerlan.task_tracker.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProjectRequest(
        @NotNull
        @Size(max = 255, message = "Title is too long")
        String title,
        @Size(max = 255, message = "Description is too long")
        String description
) {
}
