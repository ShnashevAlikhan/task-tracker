package com.temerlan.task_tracker.dto.projectDto;

import java.time.LocalDate;

public record ProjectFilter(
        String title,
        LocalDate createdFrom,
        LocalDate createdTo,
        LocalDate updateAt
) {
}
