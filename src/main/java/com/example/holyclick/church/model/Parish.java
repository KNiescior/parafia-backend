package com.example.holyclick.church.model;

import com.example.holyclick.persona.model.Rector;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Parish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    private String name;

    @OneToOne
    @JoinColumn(name = "Rector", referencedColumnName = "id")
    private Rector rectorId;
}
