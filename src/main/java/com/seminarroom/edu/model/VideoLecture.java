package com.seminarroom.edu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class VideoLecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String youtubeLink;

    @ManyToOne
    @JoinColumn(name = "module_id")
    @JsonBackReference
    private Module module;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getYoutubeLink() { return youtubeLink; }
    public void setYoutubeLink(String youtubeLink) { this.youtubeLink = youtubeLink; }

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }
}