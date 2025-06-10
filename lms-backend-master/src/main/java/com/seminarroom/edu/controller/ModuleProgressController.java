package com.seminarroom.edu.controller;

import com.seminarroom.edu.model.Module;
import com.seminarroom.edu.model.ModuleProgress;
import com.seminarroom.edu.model.User;
import com.seminarroom.edu.repository.ModuleProgressRepository;
import com.seminarroom.edu.repository.ModuleRepository;
import com.seminarroom.edu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/progress")
public class ModuleProgressController {

    @Autowired
    private ModuleProgressRepository progressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @PostMapping
    public ResponseEntity<?> saveProgress(@RequestBody ModuleProgress progress) {
        System.out.println("Incoming progress: " + progress);

        // Get userId and moduleId from the request (your logic)
        Long userId = progress.getUser() != null ? progress.getUser().getId() : null;
        Long moduleId = progress.getModule() != null ? progress.getModule().getId() : null;

        if (userId == null || moduleId == null) {
            return ResponseEntity.badRequest().body("User ID and Module ID must be provided");
        }

        // Validate if user and module exist (GitHub logic)
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Module> moduleOpt = moduleRepository.findById(moduleId);

        if (userOpt.isEmpty() || moduleOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User or Module not found");
        }

        // Fetch existing progress or initialize new one
        Optional<ModuleProgress> existingOpt = progressRepository.findByUserAndModule(userOpt.get(), moduleOpt.get());

        ModuleProgress existing = existingOpt.orElseGet(() -> {
            ModuleProgress newProgress = new ModuleProgress();
            newProgress.setUser(userOpt.get());
            newProgress.setModule(moduleOpt.get());
            newProgress.setReadingMaterial(false);
            newProgress.setVideo(false);
            newProgress.setAssignment(false);
            newProgress.setQuiz(false);
            return newProgress;
        });

        // Only update fields to true (your logic)
        if (progress.isReadingMaterial()) existing.setReadingMaterial(true);
        if (progress.isVideo()) existing.setVideo(true);
        if (progress.isAssignment()) existing.setAssignment(true);
        if (progress.isQuiz()) existing.setQuiz(true);

        ModuleProgress saved = progressRepository.save(existing);
        System.out.println("Saved progress: " + saved);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{userId}/{moduleId}")
    public ResponseEntity<?> getProgress(@PathVariable Long userId, @PathVariable Long moduleId) {
        // Validate existence
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Module> moduleOpt = moduleRepository.findById(moduleId);

        if (userOpt.isEmpty() || moduleOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User or Module not found");
        }

        Optional<ModuleProgress> progress = progressRepository.findByUserAndModule(userOpt.get(), moduleOpt.get());

        return progress.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
