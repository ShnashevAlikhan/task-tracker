package com.temerlan.task_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
public class User {

    public User() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String email;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.ROLE_USER;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public void attachProject(Project project) {
        if(project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        projects.add(project);
        project.setUser(this);
    }
    public void removeProject(Project project) {
        if(project == null) {
            throw new IllegalArgumentException("Project cannot be null");
        }
        projects.remove(project);
        project.setUser(null);
    }

    public static User create(
            String email,
            String name,
            String password
    ) {
        User user = new User();

        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);

        return user;
    }
    protected void setEmail(String email) {
        this.email = email;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setPassword(String password) {
        this.password = password;
    }
}
