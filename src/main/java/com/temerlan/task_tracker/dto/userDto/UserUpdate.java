package com.temerlan.task_tracker.dto.userDto;

import jakarta.validation.constraints.Size;

public record UserUpdate(

        String email,

        String name,

        @Size(min = 6, message = "Password is too short")
        String password
) {
}
