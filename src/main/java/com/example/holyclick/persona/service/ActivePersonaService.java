package com.example.holyclick.persona.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.holyclick.persona.model.ActivePersona;
import com.example.holyclick.persona.model.Parishioner;
import com.example.holyclick.persona.model.PersonaType;
import com.example.holyclick.persona.model.Rector;
import com.example.holyclick.persona.repository.ParishionerRepository;
import com.example.holyclick.persona.repository.RectorRepository;

@Service
@RequiredArgsConstructor
public class ActivePersonaService {

    private final ParishionerRepository parishionerRepository;
    private final RectorRepository rectorRepository;
    
    //todo get a persona using personaId and type from Active Persona
    public Optional<Parishioner> getParishioner(ActivePersona activePersona) {

        int personaId = activePersona.getPersonaId();
        PersonaType type = activePersona.getType();

        if (type == PersonaType.PARISHIONER) {
            return parishionerRepository.findById(personaId);
        }
        
        return null;
    }

    public Optional<Rector> getRector(ActivePersona activePersona) {
        int personaId = activePersona.getPersonaId();
        PersonaType type = activePersona.getType();

        if (type == PersonaType.RECTOR) {
            return rectorRepository.findById(personaId);
        }

        return null;
    }
}

