package com.example.holyclick.church.controller;

import com.example.holyclick.church.dto.ParishDTO;
import com.example.holyclick.church.dto.ParishResponseDTO;
import com.example.holyclick.church.model.Parish;
import com.example.holyclick.church.service.ParishService;
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

@Slf4j
@RestController
@RequestMapping("/api/v1/rector/parish")
@RequiredArgsConstructor
public class ParishController {

    private final ParishService parishService;
    private final UserRepository userRepository;
    private final ActivePersonaRepository activePersonaRepository;

    @PostMapping("/add")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<ParishResponseDTO> createParish(@RequestBody ParishDTO parishDTO) {
        log.info("Received request to create parish: {}", parishDTO);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        Parish parish = parishService.createParish(parishDTO, activePersona.getPersonaId());
        log.info("Successfully created parish with ID: {}", parish.getId());
        return ResponseEntity.ok(mapToResponseDTO(parish));
    }

    @PutMapping("/update")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<ParishResponseDTO> updateParish(@RequestBody ParishDTO parishDTO) {
        try {
            log.info("Received request to update parish, new data: {}", parishDTO);
            User user = userRepository.getCurrentUser();
            log.info("Current user: {}", user.getUsername());
            
            ActivePersona activePersona = activePersonaRepository.findByUser(user)
                    .orElseThrow(() -> new IllegalStateException("No active persona found"));
            log.info("Active persona ID: {}, type: {}", activePersona.getPersonaId(), activePersona.getType());

            Parish parish = parishService.updateParish(parishDTO, activePersona.getPersonaId());
            log.info("Successfully updated parish with ID: {}", parish.getId());
            return ResponseEntity.ok(mapToResponseDTO(parish));
        } catch (Exception e) {
            log.error("Error updating parish: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/my")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<ParishResponseDTO> getMyParish() {
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        Parish parish = parishService.getParishByRectorId(activePersona.getPersonaId());
        return ResponseEntity.ok(mapToResponseDTO(parish));
    }

    private ParishResponseDTO mapToResponseDTO(Parish parish) {
        ParishResponseDTO dto = new ParishResponseDTO();
        dto.setId(parish.getId());
        dto.setName(parish.getName());
        return dto;
    }
} 