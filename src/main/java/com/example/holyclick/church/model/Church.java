package com.example.holyclick.church.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Church {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    private String name;

    @OneToOne
    @JoinColumn(name = "Address" , referencedColumnName = "id")
    private Address addressId;

    @ManyToOne
    @JoinColumn(name = "Parish", referencedColumnName = "id")
    private Parish parishId;

    private Integer massAmount;
}
