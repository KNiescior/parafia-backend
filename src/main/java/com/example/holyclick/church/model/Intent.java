package com.example.holyclick.church.model;

import com.example.holyclick.persona.model.Parishioner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Intent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "Parishioner", referencedColumnName = "id")
    private Parishioner parishionerId;

    @ManyToOne
    @JoinColumn(name = "Mass", referencedColumnName = "id")
    private Mass massId;

    private String description;

    private Date date;
}
