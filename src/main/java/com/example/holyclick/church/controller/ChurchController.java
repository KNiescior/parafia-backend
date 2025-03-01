package com.example.holyclick.church.controller;

import com.example.holyclick.church.dto.ChurchDTO;
import com.example.holyclick.church.dto.ChurchResponseDTO;
import com.example.holyclick.church.model.Church;
import com.example.holyclick.church.service.ChurchService;
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
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/rector/church")
@RequiredArgsConstructor
public class ChurchController {

    private final ChurchService churchService;
    private final UserRepository userRepository;
    private final ActivePersonaRepository activePersonaRepository;

    @PostMapping("/add")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<ChurchResponseDTO> createChurch(@RequestBody ChurchDTO churchDTO) {
        log.info("Received request to create church: {}", churchDTO);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        Church church = churchService.createChurch(churchDTO, activePersona.getPersonaId());
        log.info("Successfully created church with ID: {}", church.getId());
        return ResponseEntity.ok(mapToResponseDTO(church));
    }

    @PutMapping("/update/{churchId}")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<ChurchResponseDTO> updateChurch(
            @PathVariable Integer churchId,
            @RequestBody ChurchDTO churchDTO
    ) {
        log.info("Received request to update church with ID: {}, new data: {}", churchId, churchDTO);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        Church church = churchService.updateChurch(churchId, churchDTO, activePersona.getPersonaId());
        log.info("Successfully updated church with ID: {}", church.getId());
        return ResponseEntity.ok(mapToResponseDTO(church));
    }

    @GetMapping("/all")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<List<ChurchResponseDTO>> getAllChurches() {
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        List<Church> churches = churchService.getAllChurchesByRectorId(activePersona.getPersonaId());
        List<ChurchResponseDTO> churchDTOs = churches.stream()
                .map(this::mapToResponseDTO)
                .toList();
        return ResponseEntity.ok(churchDTOs);
    }

    @DeleteMapping("/delete/{churchId}")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<Void> deleteChurch(@PathVariable Integer churchId) {
        log.info("Received request to delete church with ID: {}", churchId);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        churchService.deleteChurch(churchId, activePersona.getPersonaId());
        log.info("Successfully deleted church with ID: {}", churchId);
        return ResponseEntity.ok().build();
    }

    private ChurchResponseDTO mapToResponseDTO(Church church) {
        ChurchResponseDTO dto = new ChurchResponseDTO();
        dto.setId(church.getId());
        dto.setName(church.getName());
        dto.setStreet(church.getAddress().getStreet());
        dto.setNumber(church.getAddress().getNumber());
        dto.setCity(church.getAddress().getCity());
        dto.setPostalCode(church.getAddress().getPostalCode());
        dto.setMassAmount(church.getMassAmount());
        return dto;
    }
} 