package com.example.holyclick.persona.model;

import com.example.holyclick.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    private String name;

    private String surname;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    private User userId;
}
