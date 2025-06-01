package com.seminarroom.edu.controller;

import com.seminarroom.edu.model.Course;
import com.seminarroom.edu.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;

@RestController
// @CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseRepository repository;

    // above your existing methods
    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public List<Course> getAll() {
        return repository.findAll();
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        return repository.save(course);
    }
}
