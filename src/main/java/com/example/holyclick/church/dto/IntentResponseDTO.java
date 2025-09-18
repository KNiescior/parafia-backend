package com.example.holyclick.church.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class IntentResponseDTO {
    private Integer id;
    private String description;
    private LocalDate date;
    private Integer parishionerId;
    private String parishionerName;
    private String parishionerSurname;
    private Integer massId;
    private String massTime;
    private String massWeekday;
} 