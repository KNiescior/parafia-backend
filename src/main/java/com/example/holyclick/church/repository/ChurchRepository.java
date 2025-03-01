package com.example.holyclick.church.repository;

import com.example.holyclick.church.model.Church;
import com.example.holyclick.church.model.Parish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChurchRepository extends JpaRepository<Church, Integer> {
    List<Church> findAllByParish(Parish parish);
} 