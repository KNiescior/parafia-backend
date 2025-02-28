package com.example.holyclick.church.repository;

import com.example.holyclick.church.model.Intent;
import com.example.holyclick.church.model.Mass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntentRepository extends JpaRepository<Intent, Integer> {
    List<Intent> findAllByMass(Mass mass);
} 