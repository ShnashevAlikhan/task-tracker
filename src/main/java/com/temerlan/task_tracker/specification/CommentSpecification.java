package com.temerlan.task_tracker.specification;

import com.temerlan.task_tracker.dto.CommentFilter;
import com.temerlan.task_tracker.entity.Comment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CommentSpecification {
    public CommentSpecification(){
    }

    public static Specification<Comment> filter(
            Long projectId,
            Long taskId,
            CommentFilter filter
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(
                    root
                    .get("task")
                    .get("project")
                    .get("id"),
                    projectId));

            predicates.add(cb.equal(
                            root
                            .get("task")
                            .get("id"),
                            taskId));

            if(filter.description() != null && !filter.description().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("description")), "%" + filter.description().toLowerCase() + "%"));
            }

            if(filter.createdFrom() != null && filter.createdTo() != null &&
                    filter.createdFrom().isAfter(filter.createdTo())) {
                throw new IllegalArgumentException("createdFrom cannot be after createdTo");
            }

            if(filter.createdFrom() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), filter.createdFrom().atStartOfDay()));
            }

            if(filter.createdTo() != null) {
                predicates.add(cb.lessThan(root.get("createdAt"), filter.createdTo().plusDays(1).atStartOfDay()));
            }

            if(filter.updatedAt() != null) {
                predicates.add(cb.lessThan(root.get("updatedAt"), filter.updatedAt().plusDays(1).atStartOfDay()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
