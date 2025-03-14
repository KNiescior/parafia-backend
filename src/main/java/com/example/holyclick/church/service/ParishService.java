package com.example.holyclick.church.service;

import com.example.holyclick.church.dto.ParishDTO;
import com.example.holyclick.church.dto.ParishListItemDTO;
import com.example.holyclick.church.exception.ParishAlreadyExistsException;
import com.example.holyclick.church.exception.ParishNotFoundException;
import com.example.holyclick.church.model.Parish;
import com.example.holyclick.church.repository.ParishRepository;
import com.example.holyclick.persona.model.PersonaType;
import com.example.holyclick.persona.model.Rector;
import com.example.holyclick.persona.repository.ActivePersonaRepository;
import com.example.holyclick.persona.repository.RectorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParishService {

    private final ParishRepository parishRepository;
    private final RectorRepository rectorRepository;
    private final ActivePersonaRepository activePersonaRepository;

    @Transactional
    public Parish createParish(ParishDTO parishDTO, Integer rectorId) {
        log.info("Attempting to create parish for rector ID: {}", rectorId);
        
        // Check if rector already has a parish
        if (parishRepository.existsByRectorId_Id(rectorId)) {
            log.warn("Rector ID: {} already has a parish assigned", rectorId);
            throw new ParishAlreadyExistsException("This rector already has a parish assigned");
        }

        Rector rector = rectorRepository.findById(rectorId)
                .orElseThrow(() -> new IllegalArgumentException("Rector not found"));

        Parish parish = new Parish();
        parish.setName(parishDTO.getName());
        parish.setRector(rector);

        Parish savedParish = parishRepository.save(parish);
        log.info("Successfully created parish with ID: {} for rector ID: {}", savedParish.getId(), rectorId);
        return savedParish;
    }

    @Transactional
    public Parish updateParish(ParishDTO parishDTO, Integer rectorId) {
        Parish parish = parishRepository.findByRectorId_Id(rectorId)
                .orElseThrow(() -> new ParishNotFoundException("Parish not found for this rector"));

        parish.setName(parishDTO.getName());
        return parishRepository.save(parish);
    }

    public Parish getParishByRectorId(Integer rectorId) {
        return parishRepository.findByRectorId_Id(rectorId)
                .orElseThrow(() -> new ParishNotFoundException("Parish not found for this rector"));
    }

    public List<ParishListItemDTO> getAllParishes() {
        return parishRepository.findAll().stream()
                .map(this::mapToListItemDTO)
                .collect(Collectors.toList());
    }

    public List<ParishListItemDTO> searchParishes(String query) {
        return parishRepository.findByNameContainingIgnoreCase(query).stream()
                .map(this::mapToListItemDTO)
                .collect(Collectors.toList());
    }

    private ParishListItemDTO mapToListItemDTO(Parish parish) {
        ParishListItemDTO dto = new ParishListItemDTO();
        dto.setId(parish.getId());
        dto.setName(parish.getName());
        return dto;
    }
} 