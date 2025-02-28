package com.example.holyclick.church.dto;

import lombok.Data;

@Data
public class ChurchResponseDTO {
    private Integer id;
    private String name;
    private String street;
    private String number;
    private String city;
    private String postalCode;
    private Integer massAmount;
} 