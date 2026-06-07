package com.temerlan.task_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updateAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "project", orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public void removeTask(Task task) {
        if(task == null) throw new IllegalArgumentException("Task cannot be null");

        this.tasks.remove(task);
        task.setProject(null);
    }
    public void attachTask(Task task) {
        if(task == null) throw new IllegalArgumentException("Task cannot be null");

        this.tasks.add(task);
        task.setProject(this);
    }

    public void updateProject(
            String title,
            String description
    ) {
        if(title != null) {
            this.title = title;
        }
        if(description != null) {
            this.description = description;
        }
    }
    public static Project create(
            String title,
            String description
    ) {
        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);

        return project;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    protected void setUser(User user) {
        this.user = user;
    }

}
