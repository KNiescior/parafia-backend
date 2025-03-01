package com.example.holyclick.church.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class MassAvailabilityDTO {
    private LocalDate date;
    private List<MassTimeSlotDTO> availableMasses;
} 