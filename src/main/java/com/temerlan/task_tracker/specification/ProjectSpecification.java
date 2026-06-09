package com.temerlan.task_tracker.specification;


import com.temerlan.task_tracker.dto.projectDto.ProjectFilter;
import com.temerlan.task_tracker.entity.Project;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProjectSpecification {
    public ProjectSpecification() {
    }

    public static Specification<Project> filter(
            Long userId,
            ProjectFilter filter)
    {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get("user").get("id"), userId));

            if(filter.title() != null) {
                predicates.add(cb.like(root.get("title"), "%" + filter.title() + "%"));
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
