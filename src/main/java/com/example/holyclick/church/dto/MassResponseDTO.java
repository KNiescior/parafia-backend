package com.example.holyclick.church.dto;

import com.example.holyclick.model.Weekday;
import lombok.Data;

@Data
public class MassResponseDTO {
    private Integer id;
    private String time;
    private Weekday weekday;
    private Integer intentAmount;
    private Integer churchId;
    private String churchName;
} 