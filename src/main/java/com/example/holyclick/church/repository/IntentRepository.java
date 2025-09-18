package com.example.holyclick.church.repository;

import com.example.holyclick.church.model.Intent;
import com.example.holyclick.church.model.Mass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDate;

@Repository
public interface IntentRepository extends JpaRepository<Intent, Integer> {
    List<Intent> findAllByMass(Mass mass);
    long countByMass(Mass mass);
    List<Intent> findAllByMassAndDate(Mass mass, LocalDate date);
    long countByMassAndDate(Mass mass, LocalDate date);
} 