package com.example.holyclick.church.service;

import com.example.holyclick.church.dto.ChurchDTO;
import com.example.holyclick.church.exception.ChurchNotFoundException;
import com.example.holyclick.church.exception.ChurchNotBelongToParishException;
import com.example.holyclick.church.exception.ParishNotFoundException;
import com.example.holyclick.church.model.Address;
import com.example.holyclick.church.model.Church;
import com.example.holyclick.church.model.Parish;
import com.example.holyclick.church.repository.AddressRepository;
import com.example.holyclick.church.repository.ChurchRepository;
import com.example.holyclick.church.repository.ParishRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChurchService {

    private final ChurchRepository churchRepository;
    private final ParishRepository parishRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public Church createChurch(ChurchDTO churchDTO, Integer rectorId) {
        log.info("Creating church for rector ID: {}", rectorId);
        
        Parish parish = parishRepository.findByRectorId_Id(rectorId)
                .orElseThrow(() -> new ParishNotFoundException("Parish not found for this rector"));

        // Create and save address
        Address address = new Address();
        address.setStreet(churchDTO.getStreet());
        address.setNumber(churchDTO.getNumber());
        address.setCity(churchDTO.getCity());
        address.setPostalCode(churchDTO.getPostalCode());
        address = addressRepository.save(address);

        // Create and save church
        Church church = new Church();
        church.setName(churchDTO.getName());
        church.setAddressId(address);
        church.setParishId(parish);
        church.setMassAmount(churchDTO.getMassAmount());

        Church savedChurch = churchRepository.save(church);
        log.info("Created church with ID: {}", savedChurch.getId());
        return savedChurch;
    }

    @Transactional
    public Church updateChurch(Integer churchId, ChurchDTO churchDTO, Integer rectorId) {
        log.info("Updating church ID: {} for rector ID: {}", churchId, rectorId);
        
        Church church = churchRepository.findById(churchId)
                .orElseThrow(() -> new ChurchNotFoundException("Church not found"));

        // Verify church belongs to rector's parish
        if (!church.getParishId().getRectorId().getId().equals(rectorId)) {
            throw new ChurchNotBelongToParishException("Church does not belong to this rector's parish");
        }

        // Update address
        Address address = church.getAddressId();
        address.setStreet(churchDTO.getStreet());
        address.setNumber(churchDTO.getNumber());
        address.setCity(churchDTO.getCity());
        address.setPostalCode(churchDTO.getPostalCode());
        addressRepository.save(address);

        // Update church
        church.setName(churchDTO.getName());
        church.setMassAmount(churchDTO.getMassAmount());

        Church updatedChurch = churchRepository.save(church);
        log.info("Updated church with ID: {}", updatedChurch.getId());
        return updatedChurch;
    }

    public List<Church> getAllChurchesByRectorId(Integer rectorId) {
        log.info("Fetching all churches for rector ID: {}", rectorId);
        Parish parish = parishRepository.findByRectorId_Id(rectorId)
                .orElseThrow(() -> new ParishNotFoundException("Parish not found for this rector"));
        
        return churchRepository.findAllByParishId(parish);
    }

    @Transactional
    public void deleteChurch(Integer churchId, Integer rectorId) {
        log.info("Deleting church ID: {} for rector ID: {}", churchId, rectorId);
        
        Church church = churchRepository.findById(churchId)
                .orElseThrow(() -> new ChurchNotFoundException("Church not found"));

        // Verify church belongs to rector's parish
        if (!church.getParishId().getRectorId().getId().equals(rectorId)) {
            throw new ChurchNotBelongToParishException("Church does not belong to this rector's parish");
        }

        // Delete the church and its address
        Address address = church.getAddressId();
        churchRepository.delete(church);
        addressRepository.delete(address);
        
        log.info("Deleted church with ID: {} and its address", churchId);
    }
} 