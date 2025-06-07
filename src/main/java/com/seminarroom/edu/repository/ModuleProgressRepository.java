package com.seminarroom.edu.repository;

<<<<<<< HEAD
import com.seminarroom.edu.model.Module;
import com.seminarroom.edu.model.ModuleProgress;
import com.seminarroom.edu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleProgressRepository extends JpaRepository<ModuleProgress, Long> {
    Optional<ModuleProgress> findByUserAndModule(User user, Module module);
=======
import com.seminarroom.edu.model.ModuleProgress;
import com.seminarroom.edu.model.User;
import com.seminarroom.edu.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ModuleProgressRepository extends JpaRepository<ModuleProgress, Long> {
    Optional<ModuleProgress> findByUserAndModule(User user, Module module);
    List<ModuleProgress> findByUser(User user);
>>>>>>> 41496541e22464b94ac858c4672ccfa3b93bf8e4
}
