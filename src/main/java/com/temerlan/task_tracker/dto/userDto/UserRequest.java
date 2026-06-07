package com.temerlan.task_tracker.dto.userDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record UserRequest(

        @NotNull
        String email,

        @NotNull String name,
        @NotNull
        @Size(min = 6, message = "Password is too short")
        String password
) {
}
