package com.temerlan.task_tracker.repository;

import com.temerlan.task_tracker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByIdAndUserId(Long projectId, Long userId);

    List<Project> findProjectsByUserId(Long userId);
}
