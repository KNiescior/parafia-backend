package com.example.holyclick.church.service;

import com.example.holyclick.church.dto.MassDTO;
import com.example.holyclick.church.exception.ChurchNotBelongToParishException;
import com.example.holyclick.church.exception.ChurchNotFoundException;
import com.example.holyclick.church.exception.MassNotFoundException;
import com.example.holyclick.church.model.Church;
import com.example.holyclick.church.model.Mass;
import com.example.holyclick.church.repository.ChurchRepository;
import com.example.holyclick.church.repository.MassRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MassService {

    private final MassRepository massRepository;
    private final ChurchRepository churchRepository;

    @Transactional
    public Mass createMass(Integer churchId, MassDTO massDTO, Integer rectorId) {
        log.info("Creating mass for church ID: {} by rector ID: {}", churchId, rectorId);
        
        Church church = churchRepository.findById(churchId)
                .orElseThrow(() -> new ChurchNotFoundException("Church not found"));

        // Verify church belongs to rector's parish
        if (!church.getParishId().getRectorId().getId().equals(rectorId)) {
            throw new ChurchNotBelongToParishException("Church does not belong to this rector's parish");
        }

        Mass mass = new Mass();
        mass.setTime(massDTO.getTime());
        mass.setWeekday(massDTO.getWeekday());
        mass.setIntentAmount(massDTO.getIntentAmount());
        mass.setChurchId(church);

        Mass savedMass = massRepository.save(mass);
        log.info("Created mass with ID: {} for church ID: {}", savedMass.getId(), churchId);
        return savedMass;
    }

    @Transactional
    public Mass updateMass(Integer massId, MassDTO massDTO, Integer rectorId) {
        log.info("Updating mass ID: {} by rector ID: {}", massId, rectorId);
        
        Mass mass = massRepository.findById(massId)
                .orElseThrow(() -> new MassNotFoundException("Mass not found"));

        // Verify mass belongs to rector's parish
        if (!mass.getChurchId().getParishId().getRectorId().getId().equals(rectorId)) {
            throw new ChurchNotBelongToParishException("Mass does not belong to this rector's parish");
        }

        mass.setTime(massDTO.getTime());
        mass.setWeekday(massDTO.getWeekday());
        mass.setIntentAmount(massDTO.getIntentAmount());

        Mass updatedMass = massRepository.save(mass);
        log.info("Updated mass with ID: {}", updatedMass.getId());
        return updatedMass;
    }

    public List<Mass> getChurchMasses(Integer churchId, Integer rectorId) {
        log.info("Fetching masses for church ID: {} by rector ID: {}", churchId, rectorId);
        
        Church church = churchRepository.findById(churchId)
                .orElseThrow(() -> new ChurchNotFoundException("Church not found"));

        // Verify church belongs to rector's parish
        if (!church.getParishId().getRectorId().getId().equals(rectorId)) {
            throw new ChurchNotBelongToParishException("Church does not belong to this rector's parish");
        }

        return massRepository.findAllByChurchId(church);
    }

    @Transactional
    public void deleteMass(Integer massId, Integer rectorId) {
        log.info("Deleting mass ID: {} by rector ID: {}", massId, rectorId);
        
        Mass mass = massRepository.findById(massId)
                .orElseThrow(() -> new MassNotFoundException("Mass not found"));

        // Verify mass belongs to rector's parish
        if (!mass.getChurchId().getParishId().getRectorId().getId().equals(rectorId)) {
            throw new ChurchNotBelongToParishException("Mass does not belong to this rector's parish");
        }

        massRepository.delete(mass);
        log.info("Deleted mass with ID: {}", massId);
    }
} 