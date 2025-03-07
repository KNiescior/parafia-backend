package com.example.holyclick.church.service;

import com.example.holyclick.church.dto.MassAvailabilityDTO;
import com.example.holyclick.church.dto.MassTimeSlotDTO;
import com.example.holyclick.church.model.Address;
import com.example.holyclick.church.model.Church;
import com.example.holyclick.church.model.Intent;
import com.example.holyclick.church.model.Mass;
import com.example.holyclick.church.model.Parish;
import com.example.holyclick.church.repository.ChurchRepository;
import com.example.holyclick.church.repository.IntentRepository;
import com.example.holyclick.church.repository.MassRepository;
import com.example.holyclick.church.exception.MassNotFoundException;
import com.example.holyclick.church.exception.NoIntentSlotsAvailableException;
import com.example.holyclick.persona.model.Parishioner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntentService {
    private final MassRepository massRepository;
    private final IntentRepository intentRepository;
    private final ChurchRepository churchRepository;

    public List<MassAvailabilityDTO> getMassAvailability(Integer parishId, LocalDate startDate, LocalDate endDate) {
        Parish parish = new Parish();
        parish.setId(parishId);
        List<Church> parishChurches = churchRepository.findAllByParish(parish);
        List<Mass> masses = massRepository.findAllByChurchIn(parishChurches);
        
        return startDate.datesUntil(endDate.plusDays(1))
            .map(date -> {
                MassAvailabilityDTO availability = new MassAvailabilityDTO();
                availability.setDate(date);
                
                List<MassTimeSlotDTO> availableMasses = masses.stream()
                    .filter(mass -> isWeekdayMatch(mass, date))
                    .filter(mass -> hasAvailableSlots(mass))
                    .map(mass -> createTimeSlotDTO(mass))
                    .collect(Collectors.toList());
                
                availability.setAvailableMasses(availableMasses);
                return availability;
            })
            .filter(availability -> !availability.getAvailableMasses().isEmpty())
            .collect(Collectors.toList());
    }

    @Transactional
    public Intent createIntent(Integer massId, String description, Parishioner parishioner) {
        Mass mass = massRepository.findById(massId)
            .orElseThrow(() -> new MassNotFoundException("Mass not found"));

        if (!hasAvailableSlots(mass)) {
            throw new NoIntentSlotsAvailableException("No intent slots available for this mass");
        }

        Intent intent = new Intent();
        intent.setMass(mass);
        intent.setDescription(description);
        intent.setParishioner(parishioner);

        return intentRepository.save(intent);
    }

    private boolean isWeekdayMatch(Mass mass, LocalDate date) {
        return mass.getWeekday().name().equals(date.getDayOfWeek().name());
    }

    private boolean hasAvailableSlots(Mass mass) {
        long currentIntents = intentRepository.countByMass(mass);
        return currentIntents < mass.getIntentAmount();
    }

    private MassTimeSlotDTO createTimeSlotDTO(Mass mass) {
        MassTimeSlotDTO dto = new MassTimeSlotDTO();
        dto.setMassId(mass.getId());
        dto.setTime(mass.getTime());
        dto.setChurchId(mass.getChurch().getId());
        dto.setChurchName(mass.getChurch().getName());
        Address address = mass.getChurch().getAddress();
        dto.setChurchAddress(address.getStreet() + " " + address.getNumber() + ", " + address.getPostalCode() + " " + address.getCity());
        
        long currentIntents = intentRepository.countByMass(mass);
        dto.setAvailableIntentSlots((int)(mass.getIntentAmount() - currentIntents));
        
        return dto;
    }
} 