package com.example.holyclick.church.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "churches")
public class Church {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parish_id", nullable = false)
    private Parish parish;

    @Column(nullable = false)
    private Integer massAmount;
}
