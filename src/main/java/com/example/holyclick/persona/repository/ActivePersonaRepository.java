package com.example.holyclick.persona.repository;

import com.example.holyclick.persona.model.ActivePersona;
import com.example.holyclick.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivePersonaRepository extends JpaRepository<ActivePersona, Integer> {
    Optional<ActivePersona> findByUser(User user);
} 