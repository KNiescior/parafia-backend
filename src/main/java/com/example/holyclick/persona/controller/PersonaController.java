package com.example.holyclick.persona.controller;

import com.example.holyclick.persona.dto.*;
import com.example.holyclick.persona.exception.InvalidAdminKeyException;
import com.example.holyclick.persona.model.Parishioner;
import com.example.holyclick.persona.model.Rector;
import com.example.holyclick.persona.service.PersonaService;
import com.example.holyclick.user.model.User;
import com.example.holyclick.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/persona")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;
    private final UserRepository userRepository;
    
    @Value("${ADMIN_SECRET_KEY}")
    private String adminSecretKey;

    @PostMapping("/add/parishioner")
    public ResponseEntity<PersonaResponseDTO<Parishioner>> createParishioner(
            @RequestBody ParishionerDTO parishionerDTO
    ) {
        User user = userRepository.getCurrentUser();
        Parishioner parishioner = personaService.createParishioner(parishionerDTO, user);
        
        return ResponseEntity.ok(PersonaResponseDTO.<Parishioner>builder()
                .message("Parishioner created successfully")
                .data(parishioner)
                .build());
    }

    @PostMapping("/add/rector")
    public ResponseEntity<PersonaResponseDTO<Rector>> createRector(
            @RequestBody RectorDTO rectorDTO,
            @RequestHeader("X-Admin-Key") String adminKey
    ) {
        if (!adminKey.equals(adminSecretKey)) {
            throw new InvalidAdminKeyException("Invalid admin key");
        }

        User user = userRepository.getCurrentUser();
        Rector rector = personaService.createRector(rectorDTO, user);
        
        return ResponseEntity.ok(PersonaResponseDTO.<Rector>builder()
                .message("Rector created successfully")
                .data(rector)
                .build());
    }

    @GetMapping("/user/all")
    public ResponseEntity<PersonaResponseDTO<PersonaListDTO>> getUserPersonas() {
        User user = userRepository.getCurrentUser();
        PersonaListDTO personas = personaService.getUserPersonas(user);
        
        return ResponseEntity.ok(PersonaResponseDTO.<PersonaListDTO>builder()
                .message("Personas retrieved successfully")
                .data(personas)
                .build());
    }

    @PostMapping("/user/active")
    public ResponseEntity<PersonaResponseDTO<Void>> setActivePersona(
            @RequestBody ActivePersonaDTO activePersonaDTO
    ) {
        User user = userRepository.getCurrentUser();
        personaService.setActivePersona(user, activePersonaDTO);
        
        return ResponseEntity.ok(PersonaResponseDTO.<Void>builder()
                .message("Active persona set successfully")
                .build());
    }
} 