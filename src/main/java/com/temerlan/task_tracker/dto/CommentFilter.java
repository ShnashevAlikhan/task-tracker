package com.temerlan.task_tracker.dto;

import java.time.LocalDate;

public record CommentFilter(
        String description,
        LocalDate createdFrom,
        LocalDate createdTo,
        LocalDate updatedAt

) {
}
