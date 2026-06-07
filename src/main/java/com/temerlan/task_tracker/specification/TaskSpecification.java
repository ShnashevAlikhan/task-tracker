package com.temerlan.task_tracker.specification;

import com.temerlan.task_tracker.dto.taskDto.TaskFilter;
import com.temerlan.task_tracker.entity.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;

public final class TaskSpecification {

    private TaskSpecification() {
    }

    public static Specification<Task> filter(Long projectId,
                                             Long userId,
                                             TaskFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("project").get("id"), projectId));
            predicates.add(cb.equal(root.get("project").get("user").get("id"), userId));

            if (filter.title() != null && !filter.title().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("title")), "%" + filter.title().toLowerCase() + "%"));
            }

            if (filter.status() != null) {
                predicates.add(cb.equal(root.get("status"), filter.status()));
            }

            if (filter.priority() != null) {
                predicates.add(cb.equal(root.get("priority"), filter.priority()));
            }

            if(filter.deadlineFrom() != null && filter.deadlineTo() != null
                    && filter.deadlineFrom().isAfter(filter.deadlineTo())) {
                throw new IllegalArgumentException("deadlineFrom cannot be after deadlineTo");
            }

            if (filter.deadlineFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("deadline"), filter.deadlineFrom().atStartOfDay()));
            }

            if (filter.deadlineTo() != null) {
                predicates.add(cb.lessThan(root.get("deadline"), filter.deadlineTo().plusDays(1).atStartOfDay()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
