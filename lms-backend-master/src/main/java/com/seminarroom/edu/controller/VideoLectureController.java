package com.seminarroom.edu.controller;

import com.seminarroom.edu.model.VideoLecture;
import com.seminarroom.edu.repository.VideoLectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules/{moduleId}/video-lectures")
public class VideoLectureController {

    @Autowired
    private VideoLectureRepository repository;

    // GET /api/modules/{moduleId}/video-lectures - Get all video lectures for a module
    @GetMapping
    public List<VideoLecture> getVideoLecturesByModuleId(@PathVariable Long moduleId) {
        return repository.findByModuleId(moduleId); // Fetch video lectures for a specific module
    }

    @PostMapping
    public VideoLecture create(@RequestBody VideoLecture lecture) {
        return repository.save(lecture);
    }
}