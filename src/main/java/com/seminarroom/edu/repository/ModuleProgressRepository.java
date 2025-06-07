package com.seminarroom.edu.repository;

import com.seminarroom.edu.model.Module;
import com.seminarroom.edu.model.ModuleProgress;
import com.seminarroom.edu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleProgressRepository extends JpaRepository<ModuleProgress, Long> {
    Optional<ModuleProgress> findByUserAndModule(User user, Module module);
}
