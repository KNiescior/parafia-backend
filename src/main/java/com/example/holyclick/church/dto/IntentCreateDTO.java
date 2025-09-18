package com.example.holyclick.church.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class IntentCreateDTO {
    private String description;
    private LocalDate date;
} 