package com.temerlan.task_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentRequest(
        @NotBlank
        @Size(max = 255, message = "Description is too long")
        String description
) {
}
