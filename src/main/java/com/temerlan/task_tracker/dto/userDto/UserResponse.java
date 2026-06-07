package com.temerlan.task_tracker.dto.userDto;

public record UserResponse(
        Long id,
        String email,
        String name
) {
}
