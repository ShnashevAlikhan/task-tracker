package com.temerlan.task_tracker.dto;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

public record PageResponse<T> (
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
    public static <T, R> PageResponse<R> from(Page<T> pages,
                                              Function<T, R> mapper)
    {
        return new PageResponse<>(
                pages.getContent().stream().map(mapper).toList(),
                pages.getNumber() + 1,
                pages.getSize(),
                pages.getTotalElements(),
                pages.getTotalPages(),
                pages.isFirst(),
                pages.isLast()
        );
    }
}
