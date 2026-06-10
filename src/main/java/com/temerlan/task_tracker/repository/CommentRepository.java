package com.temerlan.task_tracker.repository;

import com.temerlan.task_tracker.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>,
        JpaSpecificationExecutor<Comment> {

    Optional<Comment> findByIdAndTask_Id(Long commentId, Long taskId);
}
