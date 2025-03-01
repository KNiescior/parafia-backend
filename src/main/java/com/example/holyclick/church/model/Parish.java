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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    private String name;

    @OneToOne
    @JoinColumn(name = "rector_id", referencedColumnName = "id")
    private Rector rector;
}
