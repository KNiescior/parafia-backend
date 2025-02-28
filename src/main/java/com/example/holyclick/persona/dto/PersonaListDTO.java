package com.example.holyclick.persona.dto;

import lombok.Data;
import java.util.List;

@Data
public class PersonaListDTO {
    private List<ParishionerDTO> parishioners;
    private List<RectorDTO> rectors;
    private PersonaDTO activePersona;
} 