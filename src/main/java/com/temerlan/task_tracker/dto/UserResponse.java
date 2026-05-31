package com.temerlan.task_tracker.dto;

public record UserResponse(
        Long id,
        String email,
        String name
) {
}
