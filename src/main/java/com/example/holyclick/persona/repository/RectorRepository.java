package com.example.holyclick.persona.repository;

import com.example.holyclick.persona.model.Rector;
import com.example.holyclick.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RectorRepository extends JpaRepository<Rector, Integer> {
    List<Rector> findAllByUserId(User user);
} 