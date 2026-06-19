package com.temerlan.task_tracker.dto.userDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record UserRequest(

        @NotBlank
        @Email
        String email,

        @NotBlank
        String name,

        @NotBlank
        @Size(min = 6, message = "Password is too short")
        String password
) {
}
