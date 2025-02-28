package com.example.holyclick.church.dto;

import com.example.holyclick.model.Weekday;
import lombok.Data;

@Data
public class MassDTO {
    private String time;
    private Weekday weekday;
    private Integer intentAmount;
} 