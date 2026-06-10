package com.temerlan.task_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Column(nullable = false, updatable = true)
    LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @PrePersist
    void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public static Comment create(String description) {
        Comment comment = new Comment();

        comment.setDescription(description);

        return comment;
    }

    public void update(String description) {
        if(description != null) {
            this.setDescription(description);
        }
    }
    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setTask(Task task) {
        this.task = task;
    }
}
