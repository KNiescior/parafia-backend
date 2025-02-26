package com.example.holyclick.church.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Mass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Church", referencedColumnName = "id")
    private Church churchId;

    private Weekday weekday;

    private String time;

    private Integer intentAmount;
}
