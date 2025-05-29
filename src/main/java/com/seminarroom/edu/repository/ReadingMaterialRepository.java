package com.seminarroom.edu.repository;

import com.seminarroom.edu.model.ReadingMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReadingMaterialRepository extends JpaRepository<ReadingMaterial, Long> {
    List<ReadingMaterial> findByModuleId(Long moduleId);
}