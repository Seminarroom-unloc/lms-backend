package com.seminarroom.edu.model;

import jakarta.persistence.*;

@Entity
<<<<<<< HEAD
public class ModuleProgress {

=======
@Table(name = "module_progress")
public class ModuleProgress {
>>>>>>> 41496541e22464b94ac858c4672ccfa3b93bf8e4
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
<<<<<<< HEAD
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    private boolean readingMaterial;
    private boolean video;
    private boolean assignment;
    private boolean quiz;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public boolean isReadingMaterial() {
        return readingMaterial;
    }

    public void setReadingMaterial(boolean readingMaterial) {
        this.readingMaterial = readingMaterial;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isAssignment() {
        return assignment;
    }

    public void setAssignment(boolean assignment) {
        this.assignment = assignment;
    }

    public boolean isQuiz() {
        return quiz;
    }

    public void setQuiz(boolean quiz) {
        this.quiz = quiz;
    }
=======
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
>>>>>>> 41496541e22464b94ac858c4672ccfa3b93bf8e4
}
