package com.seminarroom.edu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ReadingMaterial> readingMaterials;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<VideoLecture> videoLectures;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Assignment> assignments;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public List<ReadingMaterial> getReadingMaterials() { return readingMaterials; }
    public void setReadingMaterials(List<ReadingMaterial> readingMaterials) { this.readingMaterials = readingMaterials; }

    public List<VideoLecture> getVideoLectures() { return videoLectures; }
    public void setVideoLectures(List<VideoLecture> videoLectures) { this.videoLectures = videoLectures; }

    public List<Assignment> getAssignments() { return assignments; }
    public void setAssignments(List<Assignment> assignments) { this.assignments = assignments; }
}