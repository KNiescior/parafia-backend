package com.example.holyclick.church.controller;

import com.example.holyclick.church.dto.MassDTO;
import com.example.holyclick.church.dto.MassResponseDTO;
import com.example.holyclick.church.model.Mass;
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
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/rector/mass")
@RequiredArgsConstructor
public class MassController {

    private final MassService massService;
    private final UserRepository userRepository;
    private final ActivePersonaRepository activePersonaRepository;

    @PostMapping("/church/{churchId}/add")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<MassResponseDTO> createMass(
            @PathVariable Integer churchId,
            @RequestBody MassDTO massDTO
    ) {
        log.info("Received request to create mass in church {}: {}", churchId, massDTO);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        Mass mass = massService.createMass(churchId, massDTO, activePersona.getPersonaId());
        log.info("Successfully created mass with ID: {}", mass.getId());
        return ResponseEntity.ok(mapToResponseDTO(mass));
    }

    @PutMapping("/{massId}")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<MassResponseDTO> updateMass(
            @PathVariable Integer massId,
            @RequestBody MassDTO massDTO
    ) {
        log.info("Received request to update mass {}: {}", massId, massDTO);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        Mass mass = massService.updateMass(massId, massDTO, activePersona.getPersonaId());
        log.info("Successfully updated mass with ID: {}", mass.getId());
        return ResponseEntity.ok(mapToResponseDTO(mass));
    }

    @GetMapping("/church/{churchId}")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<List<MassResponseDTO>> getChurchMasses(@PathVariable Integer churchId) {
        log.info("Received request to get masses for church: {}", churchId);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        List<Mass> masses = massService.getChurchMasses(churchId, activePersona.getPersonaId());
        List<MassResponseDTO> massResponses = masses.stream()
                .map(this::mapToResponseDTO)
                .toList();
        return ResponseEntity.ok(massResponses);
    }

    @DeleteMapping("/delete/{massId}")
    @RequirePersonaType(PersonaType.RECTOR)
    public ResponseEntity<Map<String, String>> deleteMass(@PathVariable Integer massId) {
        log.info("Received request to delete mass: {}", massId);
        User user = userRepository.getCurrentUser();
        ActivePersona activePersona = activePersonaRepository.findByUser(user)
                .orElseThrow(() -> new IllegalStateException("No active persona found"));

        massService.deleteMass(massId, activePersona.getPersonaId());
        log.info("Successfully deleted mass with ID: {}", massId);
        return ResponseEntity.ok(Map.of("message", "Mass deleted successfully"));
    }

    private MassResponseDTO mapToResponseDTO(Mass mass) {
        MassResponseDTO dto = new MassResponseDTO();
        dto.setId(mass.getId());
        dto.setTime(mass.getTime());
        dto.setWeekday(mass.getWeekday());
        dto.setIntentAmount(mass.getIntentAmount());
        dto.setChurchId(mass.getChurch().getId());
        dto.setChurchName(mass.getChurch().getName());
        return dto;
    }
} 