package com.seminarroom.edu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "module_progress")
public class ModuleProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    private boolean completed;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}
