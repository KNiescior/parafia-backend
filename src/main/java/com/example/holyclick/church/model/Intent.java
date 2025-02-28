package com.example.holyclick.church.model;

import com.example.holyclick.persona.model.Parishioner;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "intents")
public class Intent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mass_id", nullable = false)
    private Mass mass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parishioner_id", nullable = false)
    private Parishioner parishioner;
}
