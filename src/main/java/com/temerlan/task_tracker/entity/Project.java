package com.temerlan.task_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "project")
    private List<Task> task = new ArrayList<>();


    public static Project create(
            String title,
            String description
    ) {
        Project project = new Project();
        project.setTitle(title);
        project.setDescription(description);

        return project;
    }

    public void update(
            String title,
            String description
    ) {
        if(title != null) this.setTitle(title);
        if(description != null) this.setDescription(description);

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

    private void setTask(List<Task> task) {
        this.task = task;
    }
}
