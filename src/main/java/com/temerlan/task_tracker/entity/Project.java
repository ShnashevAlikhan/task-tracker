package com.temerlan.task_tracker.entity;

import com.temerlan.task_tracker.exception.TaskNotFoundException;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "project", orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();


    public Task updateTask(Long taskId,
                            String title,
                            String description,
                            TaskStatus status,
                            TaskPriority priority,
                            LocalDateTime deadline) {
        Task task = findTaskById(taskId);

        if(title != null) {
            task.setTitle(title);
        }
        if(description != null) {
            task.setDescription(description);
        }
        if(status != null) {
            task.setStatus(status);
        }
        if(priority != null) {
            task.setPriority(priority);
        }
        if(deadline != null) {
            task.setDeadline(deadline);
        }

        return task;
    }
    public void removeTask(Long taskId) {
        if(taskId == null) throw new IllegalArgumentException("Task cannot be null");

        Task task = findTaskById(taskId);

        this.tasks.remove(task);
        task.setProject(null);
    }
    public void attachTask(Task task) {
        if(task == null) throw new IllegalArgumentException("Task cannot be null");

        this.tasks.add(task);
        task.setProject(this);
    }

    private Task findTaskById(Long taskId) {
        return this.tasks.stream()
                .filter(t -> Objects.equals(t.getId(), taskId))
                .findFirst()
                .orElseThrow(() -> new TaskNotFoundException("Task with id: " + taskId + " not found"));
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

    private void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
