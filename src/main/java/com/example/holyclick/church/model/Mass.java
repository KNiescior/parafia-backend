package com.example.holyclick.church.model;

import com.example.holyclick.model.Weekday;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "masses")
public class Mass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String time;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Weekday weekday;

    @Column(nullable = false)
    private Integer intentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "church_id", nullable = false)
    private Church church;
}
