package com.seminarroom.edu.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long hours;
    private Long lectures;
    private Long exercises;

    private String title;
    private String description;
    private String trainer_name;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Module> modules;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // public Long getHrs() { return hours; }
    // public void setHrs(Long hours) { this.hours = hours; }

    // public Long getLectures() { return lectures; }
    // public void setLectures(Long lectures) { this.lectures = lectures; }

    // public Long getExercises() { return exercises; }
    // public void setExercises(Long exercises) { this.exercises = exercises; }
    public Long getHours() { return hours; }
public void setHours(Long hours) { this.hours = hours; }

public Long getLectures() { return lectures; }
public void setLectures(Long lectures) { this.lectures = lectures; }

public Long getExercises() { return exercises; }
public void setExercises(Long exercises) { this.exercises = exercises; }

    public String getTrainer_name() { return trainer_name; }
    public void setTrainer_name(String trainer_name) { this.trainer_name = trainer_name; }

    public List<Module> getModules() { return modules; }
    public void setModules(List<Module> modules) { this.modules = modules; }
}