package com.seminarroom.edu.controller;

import com.seminarroom.edu.model.Assignment;
import com.seminarroom.edu.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses/{courseId}/modules/{moduleId}/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository repository;

    @GetMapping
    public List<Assignment> getAssignmentsByModuleId(@PathVariable Long moduleId) {
        return repository.findByModuleId(moduleId);
    }

    // GET /api/courses/{courseId}/modules/{moduleId}/assignments/{assignmentId} - Get a specific assignment by ID
    @GetMapping("/{assignmentId}")
    public Assignment getAssignmentById(@PathVariable Long courseId, @PathVariable Long moduleId, @PathVariable Long assignmentId) {
        // Ensure that the assignment belongs to the correct courseId and moduleId
        Optional<Assignment> assignment = repository.findByIdAndModuleIdAndCourseId(assignmentId, moduleId, courseId);

        if (assignment.isPresent()) {
            return assignment.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
        }
    }


    @PostMapping
    public Assignment create(@RequestBody Assignment assignment) {
        return repository.save(assignment);
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> handleFileUpload(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String uploadDirPath = System.getProperty("user.dir") + "/uploads/";
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String uploadPath = uploadDirPath + fileName;
        file.transferTo(new File(uploadPath));

        Assignment assignment = repository.findById(id).orElse(null);
        if (assignment != null) {
            assignment.setUploadUrl("/uploads/" + fileName);
            repository.save(assignment);
        }

        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws IOException {
        Path filePath = Paths.get(System.getProperty("user.dir") + "/uploads/" + filename);
        Resource resource = new UrlResource(filePath.toUri());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
    }
}