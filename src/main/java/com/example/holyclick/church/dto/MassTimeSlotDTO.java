package com.example.holyclick.church.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class MassTimeSlotDTO {
    private Integer massId;
    private String time;
    private Integer churchId;
    private String churchName;
    private String churchAddress;
    private Integer availableIntentSlots;
} 