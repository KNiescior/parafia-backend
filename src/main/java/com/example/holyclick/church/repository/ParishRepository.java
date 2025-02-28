package com.example.holyclick.church.repository;

import com.example.holyclick.church.model.Parish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParishRepository extends JpaRepository<Parish, Integer> {
    boolean existsByRectorId_Id(Integer rectorId);
    Optional<Parish> findByRectorId_Id(Integer rectorId);
} 