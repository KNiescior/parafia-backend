package com.example.holyclick.church.controller;

import com.example.holyclick.church.dto.IntentResponseDTO;
import com.example.holyclick.church.model.Intent;
import com.example.holyclick.church.model.Mass;
import com.example.holyclick.church.repository.IntentRepository;
import com.example.holyclick.church.service.MassService;
import com.example.holyclick.persona.annotation.RequirePersonaType;
import com.example.holyclick.persona.model.PersonaType;
import com.example.holyclick.persona.model.ActivePersona;
import com.example.holyclick.persona.repository.ActivePersonaRepository;
import com.example.holyclick.user.model.User;
import com.example.holyclick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/rector/intent")
@RequiredArgsConstructor
public class IntentController {

    private final IntentRepository intentRepository;
    private final MassService massService;
    private final UserRepository userRepository;
    private final ActivePersonaRepository activePersonaRepository;

    @GetMapping("/mass/{massId}")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<List<IntentResponseDTO>> getMassIntents(@PathVariable Integer massId) {
        log.info("Received request to get intents for mass: {}", massId);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        Mass mass = massService.getMassById(massId, activePersona.getPersonaId());
        List<Intent> intents = intentRepository.findAllByMass(mass);
        
        List<IntentResponseDTO> intentResponses = intents.stream()
                .map(this::mapToResponseDTO)
                .toList();
        
        return ResponseEntity.ok(intentResponses);
    }

    private IntentResponseDTO mapToResponseDTO(Intent intent) {
        IntentResponseDTO dto = new IntentResponseDTO();
        dto.setId(intent.getId());
        dto.setDescription(intent.getDescription());
        dto.setParishionerId(intent.getParishioner().getId());
        dto.setParishionerFirstName(intent.getParishioner().getName());
        dto.setParishionerLastName(intent.getParishioner().getSurname());
        dto.setMassId(intent.getMass().getId());
        dto.setMassTime(intent.getMass().getTime());
        dto.setMassWeekday(intent.getMass().getWeekday().toString());
        
        // Add church info
        dto.setChurchId(intent.getMass().getChurch().getId());
        dto.setChurchName(intent.getMass().getChurch().getName());
        dto.setChurchStreet(intent.getMass().getChurch().getAddress().getStreet());
        dto.setChurchHouseNumber(intent.getMass().getChurch().getAddress().getNumber());
        dto.setChurchApartmentNumber(null); // Address doesn't have apartment number
        dto.setChurchPostalCode(intent.getMass().getChurch().getAddress().getPostalCode());
        dto.setChurchCity(intent.getMass().getChurch().getAddress().getCity());
        
        return dto;
    }
} 