package com.seminarroom.edu.repository;

import com.seminarroom.edu.model.ModuleProgress;
import com.seminarroom.edu.model.User;
import com.seminarroom.edu.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ModuleProgressRepository extends JpaRepository<ModuleProgress, Long> {
    Optional<ModuleProgress> findByUserAndModule(User user, Module module);
    List<ModuleProgress> findByUser(User user);
}
