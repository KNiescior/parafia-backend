package com.example.holyclick.church.controller;

import com.example.holyclick.church.dto.*;
import com.example.holyclick.church.model.Address;
import com.example.holyclick.church.model.Intent;
import com.example.holyclick.church.service.IntentService;
import com.example.holyclick.church.service.ParishService;
import com.example.holyclick.persona.annotation.RequirePersonaType;
import com.example.holyclick.persona.model.PersonaType;
import com.example.holyclick.persona.model.ActivePersona;
import com.example.holyclick.persona.model.Parishioner;
import com.example.holyclick.persona.repository.ActivePersonaRepository;
import com.example.holyclick.persona.service.ActivePersonaService;
import com.example.holyclick.user.model.User;
import com.example.holyclick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/parishioner/intent")
@RequiredArgsConstructor
public class ParishionerIntentController {

    private final IntentService intentService;
    private final ParishService parishService;
    private final UserRepository userRepository;
    private final ActivePersonaRepository activePersonaRepository;
    private final ActivePersonaService activePersonaService;

    @GetMapping("/parishes")
    @RequirePersonaType(PersonaType.PARISHIONER)
    public ResponseEntity<List<ParishListItemDTO>> getParishes() {
        log.info("Received request to get parishes list");
        return ResponseEntity.ok(parishService.getAllParishes());
    }

    @GetMapping("/parishes/search")
    @RequirePersonaType(PersonaType.PARISHIONER)
    public ResponseEntity<List<ParishListItemDTO>> searchParishes(
            @RequestParam(required = false, defaultValue = "") String query
    ) {
        log.info("Received request to search parishes with query: {}", query);
        return ResponseEntity.ok(parishService.searchParishes(query));
    }

    @GetMapping("/parish/{parishId}/availability")
    @RequirePersonaType(PersonaType.PARISHIONER)
    public ResponseEntity<List<MassAvailabilityDTO>> getMassAvailability(
            @PathVariable Integer parishId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        log.info("Received request to get mass availability for parish {} between {} and {}", 
                parishId, startDate, endDate);
        return ResponseEntity.ok(intentService.getMassAvailability(parishId, startDate, endDate));
    }

    @PostMapping("/mass/{massId}/intent")
    @RequirePersonaType(PersonaType.PARISHIONER)
    public ResponseEntity<IntentResponseDTO> createIntent(
            @PathVariable Integer massId,
            @RequestBody IntentCreateDTO intentCreateDTO
    ) {
        log.info("Received request to create intent for mass {}: {}", massId, intentCreateDTO);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));
        Optional<Parishioner> parishioner = activePersonaService.getParishioner(activePersona);

        if (parishioner.isEmpty()) {
            throw new IllegalStateException("Parishioner not found");
        }

        Intent intent = intentService.createIntent(
                massId,
                intentCreateDTO.getDescription(),
                parishioner.get()
        );
        return ResponseEntity.ok(mapToResponseDTO(intent));
    }

    private IntentResponseDTO mapToResponseDTO(Intent intent) {
        IntentResponseDTO dto = new IntentResponseDTO();
        dto.setId(intent.getId());
        dto.setDescription(intent.getDescription());
        
        // Parishioner info
        dto.setParishionerId(intent.getParishioner().getId());
        dto.setParishionerName(intent.getParishioner().getName());
        dto.setParishionerSurname(intent.getParishioner().getSurname());
        
        // Mass info
        dto.setMassId(intent.getMass().getId());
        dto.setMassTime(intent.getMass().getTime());
        dto.setMassWeekday(intent.getMass().getWeekday().toString());
        
        return dto;
    }
} 