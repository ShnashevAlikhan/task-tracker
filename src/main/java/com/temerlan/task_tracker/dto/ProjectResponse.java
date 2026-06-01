package com.temerlan.task_tracker.dto;

public record ProjectResponse(
        Long id,
        String title,
        String description
) {
}
