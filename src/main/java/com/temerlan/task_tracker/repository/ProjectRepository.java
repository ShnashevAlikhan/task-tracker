package com.temerlan.task_tracker.repository;

import com.temerlan.task_tracker.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> ,
        JpaSpecificationExecutor<Project> {
    Optional<Project> findByIdAndUserId(Long projectId, Long userId);



}
