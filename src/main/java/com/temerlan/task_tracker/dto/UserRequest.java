package com.temerlan.task_tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull @NotBlank String email,
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String password
) {
}
