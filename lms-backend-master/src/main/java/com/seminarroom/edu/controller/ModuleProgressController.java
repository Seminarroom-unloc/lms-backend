package com.seminarroom.edu.controller;

import com.seminarroom.edu.model.ModuleProgress;
import com.seminarroom.edu.repository.ModuleProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/progress")
public class ModuleProgressController {

    @Autowired
    private ModuleProgressRepository repository;

    @PostMapping
    public ResponseEntity<ModuleProgress> saveProgress(@RequestBody ModuleProgress progress) {
        Optional<ModuleProgress> existing = repository.findByUserIdAndModuleId(progress.getUserId(), progress.getModuleId());

        ModuleProgress toSave = existing.map(existingProgress -> {
            existingProgress.setReadingMaterial(progress.isReadingMaterial());
            existingProgress.setVideo(progress.isVideo());
            existingProgress.setAssignment(progress.isAssignment());
            existingProgress.setQuiz(progress.isQuiz());
            return existingProgress;
        }).orElse(progress);

        return ResponseEntity.ok(repository.save(toSave));
    }

    @GetMapping("/{userId}/{moduleId}")
    public ResponseEntity<ModuleProgress> getProgress(@PathVariable Long userId, @PathVariable Long moduleId) {
        return repository.findByUserIdAndModuleId(userId, moduleId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
