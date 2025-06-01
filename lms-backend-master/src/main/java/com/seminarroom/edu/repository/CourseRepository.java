package com.seminarroom.edu.repository;

import com.seminarroom.edu.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}