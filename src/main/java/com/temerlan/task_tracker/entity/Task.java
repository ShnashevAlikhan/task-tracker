package com.temerlan.task_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column()
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.NEW;

    @Enumerated(EnumType.STRING)
    private TaskPriority priority = TaskPriority.LOW;

    @Column()
    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projects_id")
    private Project project;

    public static Task create(
            String title,
            String description,
            TaskStatus status,
            TaskPriority priority,
            LocalDateTime deadline
    ){
        Task task = new Task();

        task.setTitle(title);
        task.setDescription(description);
        if(status != null) task.setStatus(status);
        if(priority != null) task.setPriority(priority);
        task.setDeadline(deadline);

        return task;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setStatus(TaskStatus status) {
        this.status = status;
    }

    protected void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    protected void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    protected void setProject(Project project) {
        this.project = project;
    }

}
