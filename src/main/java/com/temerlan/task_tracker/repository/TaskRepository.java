package com.temerlan.task_tracker.repository;

import com.temerlan.task_tracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByProjectId(Long projectId);

    Optional<Task> findByIdAndProject_IdAndProject_User_Id(
            Long taskId,
            Long projectId,
            Long userId);
}
