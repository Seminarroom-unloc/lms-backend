package com.seminarroom.edu.controller;

import com.seminarroom.edu.model.Module;
import com.seminarroom.edu.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/courses/{courseId}/modules")
public class ModuleController {

    @Autowired
    private ModuleRepository repository;

    // GET /api/courses/{courseId}/modules - Get all modules for a specific course
    @GetMapping
    public List<Module> getModulesByCourseId(@PathVariable Long courseId) {
        List<Module> modules = repository.findByCourseId(courseId); // Fetch all modules for a specific course
        if (modules.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No modules found for this course");
        }
        return modules;
    }

    // GET /api/courses/{courseId}/modules/{moduleId} - Get a specific module by its ID
    @GetMapping("/{moduleId}")
    public Module getModuleById(@PathVariable Long courseId, @PathVariable Long moduleId) {
        // Assuming the courseId is necessary for the lookup; ensure the module belongs to the course
        return repository.findByCourseIdAndId(courseId, moduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found"));
    }
}
