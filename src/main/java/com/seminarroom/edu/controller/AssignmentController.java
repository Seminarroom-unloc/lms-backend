package com.seminarroom.edu.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.seminarroom.edu.model.Assignment;
import com.seminarroom.edu.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/courses/{courseId}/modules/{moduleId}/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentRepository repository;

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${bucket.name}")
    private String bucketName;

    @GetMapping
    public List<Assignment> getAssignmentsByModuleId(@PathVariable Long moduleId) {
        return repository.findByModuleId(moduleId);
    }

    @GetMapping("/{assignmentId}")
    public Assignment getAssignmentById(@PathVariable Long courseId, @PathVariable Long moduleId, @PathVariable Long assignmentId) {
        Optional<Assignment> assignment = repository.findByIdAndModuleIdAndCourseId(assignmentId, moduleId, courseId);
        return assignment.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found"));
    }

    @PostMapping
    public Assignment create(@RequestBody Assignment assignment) {
        return repository.save(assignment);
    }

    // Upload assignment file to S3
    @PostMapping("/upload/{assignmentId}")
    public ResponseEntity<String> uploadAssignmentFileToS3(
            @PathVariable Long assignmentId,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            // Generate a unique file name
            String originalFilename = file.getOriginalFilename();
            String s3Key = "assignments/" + UUID.randomUUID() + "_" + originalFilename;

            // Prepare file metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // Upload to S3
            amazonS3.putObject(bucketName, s3Key, file.getInputStream(), metadata);

            // Generate public URL
            String fileUrl = "https://" + bucketName + ".s3.amazonaws.com/" + s3Key;

            // Save URL in the assignment record
            Optional<Assignment> optionalAssignment = repository.findById(assignmentId);
            if (optionalAssignment.isPresent()) {
                Assignment assignment = optionalAssignment.get();
                assignment.setUploadUrl(fileUrl);
                repository.save(assignment);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Assignment not found");
            }

            return ResponseEntity.ok("File uploaded to S3: " + fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }
}
