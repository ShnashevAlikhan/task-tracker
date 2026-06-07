package com.temerlan.task_tracker.dto.projectDto;


import jakarta.validation.constraints.Size;

public record ProjectUpdate(

        @Size(max = 255, message = "Title is too long")
        String title,
        @Size(max = 255, message = "Description is too long")
        String description
) {
}
