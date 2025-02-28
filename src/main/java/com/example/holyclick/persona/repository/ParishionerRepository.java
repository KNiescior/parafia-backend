package com.example.holyclick.persona.repository;

import com.example.holyclick.persona.model.Parishioner;
import com.example.holyclick.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParishionerRepository extends JpaRepository<Parishioner, Integer> {
    List<Parishioner> findAllByUserId(User user);
} 