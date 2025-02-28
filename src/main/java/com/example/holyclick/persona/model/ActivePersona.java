package com.example.holyclick.persona.model;

import com.example.holyclick.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ActivePersona {
    @Id
    private Integer userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    private Integer personaId;
    
    @Enumerated(EnumType.STRING)
    private PersonaType type;
} 