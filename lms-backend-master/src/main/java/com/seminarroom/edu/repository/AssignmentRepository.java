package com.seminarroom.edu.repository;

import com.seminarroom.edu.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByModuleId(Long moduleId);

    @Query("SELECT a FROM Assignment a WHERE a.id = :assignmentId AND a.module.id = :moduleId AND a.module.course.id = :courseId")
    Optional<Assignment> findByIdAndModuleIdAndCourseId(Long assignmentId, Long moduleId, Long courseId);
}