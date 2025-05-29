package com.seminarroom.edu.repository;

import com.seminarroom.edu.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    List<Module> findByCourseId(Long courseId);

    // Method to find a specific module by course ID and module ID
    Optional<Module> findByCourseIdAndId(Long courseId, Long moduleId);
}