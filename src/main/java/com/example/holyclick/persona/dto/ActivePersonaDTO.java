package com.example.holyclick.persona.dto;

import com.example.holyclick.persona.model.PersonaType;
import lombok.Data;

@Data
public class ActivePersonaDTO {
    private Integer personaId;
    private PersonaType type;
} 