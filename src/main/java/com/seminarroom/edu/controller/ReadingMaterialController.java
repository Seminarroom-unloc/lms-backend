package com.seminarroom.edu.controller;

import com.seminarroom.edu.model.ReadingMaterial;
import com.seminarroom.edu.repository.ReadingMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/modules/{moduleId}/reading-materials")
public class ReadingMaterialController {

    @Autowired
    private ReadingMaterialRepository repository;

    @GetMapping
    public List<ReadingMaterial> getReadingMaterialsByModuleId(
            @PathVariable Long moduleId) {
        return repository.findByModuleId(moduleId);
    }
}
