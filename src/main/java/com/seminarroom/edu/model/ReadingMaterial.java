package com.seminarroom.edu.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ReadingMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // Store the file URL or key in S3 (not the actual PDF file)
<<<<<<< HEAD
    private String s3url;
=======
    private String s3Url;
>>>>>>> 41496541e22464b94ac858c4672ccfa3b93bf8e4

    @ManyToOne
    @JoinColumn(name = "module_id")
    @JsonBackReference
    private Module module;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

<<<<<<< HEAD
    public String getS3Url() { return s3url; }
    public void setS3Url(String s3url) { this.s3url = s3url; }
=======
    public String getS3Url() { return s3Url; }
    public void setS3Url(String s3Url) { this.s3Url = s3Url; }
>>>>>>> 41496541e22464b94ac858c4672ccfa3b93bf8e4

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }
}
