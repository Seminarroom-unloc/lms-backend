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
        if (progress.getUser() == null || progress.getUser().getId() == null ||
                progress.getModule() == null || progress.getModule().getId() == null) {
            return ResponseEntity.badRequest().body("User ID and Module ID must be provided");
        }

        Long userId = progress.getUser().getId();
        Long moduleId = progress.getModule().getId();

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Module> moduleOpt = moduleRepository.findById(moduleId);

        if (userOpt.isEmpty() || moduleOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User or Module not found");
        }

        Optional<ModuleProgress> existing = progressRepository.findByUserAndModule(userOpt.get(), moduleOpt.get());

        ModuleProgress toSave = existing.orElseGet(ModuleProgress::new);
        toSave.setUser(userOpt.get());
        toSave.setModule(moduleOpt.get());

        toSave.setReadingMaterial(progress.isReadingMaterial());
        toSave.setVideo(progress.isVideo());
        toSave.setAssignment(progress.isAssignment());
        toSave.setQuiz(progress.isQuiz());

        return ResponseEntity.ok(progressRepository.save(toSave));
    }

    @GetMapping("/{userId}/{moduleId}")
    public ResponseEntity<?> getProgress(@PathVariable Long userId, @PathVariable Long moduleId) {
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
