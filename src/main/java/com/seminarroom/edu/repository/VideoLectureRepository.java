package com.seminarroom.edu.repository;

import com.seminarroom.edu.model.VideoLecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoLectureRepository extends JpaRepository<VideoLecture, Long> {
    List<VideoLecture> findByModuleId(Long moduleId);
}