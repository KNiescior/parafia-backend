package com.example.holyclick.persona.dto;

import com.example.holyclick.persona.model.PersonaType;
import lombok.Data;

@Data
public class PersonaDTO {
    private Integer id;
    private String name;
    private String surname;
    private PersonaType type;
} 