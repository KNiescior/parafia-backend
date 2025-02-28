package com.example.holyclick.persona.service;

import com.example.holyclick.persona.dto.ActivePersonaDTO;
import com.example.holyclick.persona.dto.ParishionerDTO;
import com.example.holyclick.persona.dto.RectorDTO;
import com.example.holyclick.persona.dto.PersonaListDTO;
import com.example.holyclick.persona.dto.PersonaDTO;
import com.example.holyclick.persona.exception.PersonaNotFoundException;
import com.example.holyclick.persona.exception.PersonaNotBelongToUserException;
import com.example.holyclick.persona.exception.InvalidPersonaTypeException;
import com.example.holyclick.persona.model.Parishioner;
import com.example.holyclick.persona.model.Rector;
import com.example.holyclick.persona.model.PersonaType;
import com.example.holyclick.persona.repository.ParishionerRepository;
import com.example.holyclick.persona.repository.RectorRepository;
import com.example.holyclick.user.model.User;
import com.example.holyclick.user.repository.UserRepository;
import com.example.holyclick.persona.repository.ActivePersonaRepository;
import com.example.holyclick.persona.model.ActivePersona;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final ParishionerRepository parishionerRepository;
    private final RectorRepository rectorRepository;
    private final UserRepository userRepository;
    private final ActivePersonaRepository activePersonaRepository;

    @Transactional
    public Parishioner createParishioner(ParishionerDTO dto, User userFromContext) {
        // Fetch the actual user from database
        Optional<User> user = userRepository.findByUsername(userFromContext.getUsername());

        if (user.isEmpty()) {
            throw new PersonaNotFoundException("User not found");
        }

        Parishioner parishioner = new Parishioner();

        parishioner.setName(dto.getName());
        parishioner.setSurname(dto.getSurname());
        parishioner.setPhoneNumber(dto.getPhoneNumber());
        parishioner.setUserId(user.get());
        return parishionerRepository.save(parishioner);
    }

    @Transactional
    public Rector createRector(RectorDTO dto, User user) {
        // First create and save the Rector
        Rector rector = new Rector();
        rector.setName(dto.getName());
        rector.setSurname(dto.getSurname());
        
        // Save rector first
        rector = rectorRepository.save(rector);
        
        // Then set and save the relationship
        rector.setUserId(user);
        return rectorRepository.save(rector);
    }

    public PersonaListDTO getUserPersonas(User user) {
        PersonaListDTO response = new PersonaListDTO();
        
        // Convert Parishioners to DTOs
        List<ParishionerDTO> parishioners = parishionerRepository.findAllByUserId(user)
                .stream()
                .map(this::convertToDTO)
                .toList();
        
        // Convert Rectors to DTOs
        List<RectorDTO> rectors = rectorRepository.findAllByUserId(user)
                .stream()
                .map(this::convertToDTO)
                .toList();

        // Get active persona
        Object activePersona = getActivePersona(user);
        PersonaDTO activePersonaDTO = null;
        if (activePersona != null) {
            if (activePersona instanceof Parishioner) {
                activePersonaDTO = convertToPersonaDTO((Parishioner) activePersona, PersonaType.PARISHIONER);
            } else if (activePersona instanceof Rector) {
                activePersonaDTO = convertToPersonaDTO((Rector) activePersona, PersonaType.RECTOR);
            }
        }

        response.setParishioners(parishioners);
        response.setRectors(rectors);
        response.setActivePersona(activePersonaDTO);

        return response;
    }

    private ParishionerDTO convertToDTO(Parishioner parishioner) {
        ParishionerDTO dto = new ParishionerDTO();
        dto.setName(parishioner.getName());
        dto.setSurname(parishioner.getSurname());
        dto.setPhoneNumber(parishioner.getPhoneNumber());
        return dto;
    }

    private RectorDTO convertToDTO(Rector rector) {
        RectorDTO dto = new RectorDTO();
        dto.setName(rector.getName());
        dto.setSurname(rector.getSurname());
        return dto;
    }

    private PersonaDTO convertToPersonaDTO(Parishioner parishioner, PersonaType type) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(parishioner.getId());
        dto.setName(parishioner.getName());
        dto.setSurname(parishioner.getSurname());
        dto.setType(type);
        return dto;
    }

    private PersonaDTO convertToPersonaDTO(Rector rector, PersonaType type) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(rector.getId());
        dto.setName(rector.getName());
        dto.setSurname(rector.getSurname());
        dto.setType(type);
        return dto;
    }

    @Transactional
    public void setActivePersona(User user, ActivePersonaDTO activePersonaDTO) {
        // Verify persona exists and belongs to user
        switch (activePersonaDTO.getType()) {
            case PARISHIONER -> {
                Parishioner parishioner = parishionerRepository.findById(activePersonaDTO.getPersonaId())
                        .orElseThrow(() -> new PersonaNotFoundException("Parishioner not found"));
                if (!parishioner.getUserId().equals(user)) {
                    throw new PersonaNotBelongToUserException("Parishioner does not belong to user");
                }
            }
            case RECTOR -> {
                Rector rector = rectorRepository.findById(activePersonaDTO.getPersonaId())
                        .orElseThrow(() -> new PersonaNotFoundException("Rector not found"));
                if (!rector.getUserId().equals(user)) {
                    throw new PersonaNotBelongToUserException("Rector does not belong to user");
                }
            }
            default -> throw new InvalidPersonaTypeException("Invalid persona type");
        }

        // Get or create active persona
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElse(new ActivePersona());
        
        activePersona.setUser(user);
        activePersona.setPersonaId(activePersonaDTO.getPersonaId());
        activePersona.setType(activePersonaDTO.getType());
        
        activePersonaRepository.save(activePersona);
    }

    public Object getActivePersona(User user) {
        return activePersonaRepository.findByUser(user)
                .map(active -> switch (active.getType()) {
                    case PARISHIONER -> parishionerRepository.findById(active.getPersonaId())
                            .orElse(null);
                    case RECTOR -> rectorRepository.findById(active.getPersonaId())
                            .orElse(null);
                })
                .orElse(null);
    }
} 