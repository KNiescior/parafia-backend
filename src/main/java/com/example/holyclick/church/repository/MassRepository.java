package com.example.holyclick.church.repository;

import com.example.holyclick.church.model.Church;
import com.example.holyclick.church.model.Mass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MassRepository extends JpaRepository<Mass, Integer> {
    List<Mass> findAllByChurch(Church church);
    List<Mass> findAllByChurchIn(List<Church> churches);
} 