package com.example.holyclick.church.dto;

import lombok.Data;

@Data
public class ChurchDTO {
    private String name;
    private String street;
    private String number;
    private String city;
    private String postalCode;
    private Integer massAmount;
} 