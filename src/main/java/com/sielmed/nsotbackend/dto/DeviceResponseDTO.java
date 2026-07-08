package com.sielmed.nsotbackend.dto;

import com.sielmed.nsotbackend.enums.Criticality;
import com.sielmed.nsotbackend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponseDTO {

    private Long id;
    private String hostname;

    private SiteResponseDTO site;
    private DeviceRoleResponseDTO deviceRole;
    private ManufacturerResponseDTO manufacturer;

    private String model;
    private String serialNumber;
    private String managementIp;
    private String os;
    private String currentVersion;
    private String rack;
    private String rackPosition;
    private Status status;
    private Criticality criticality;
    private String owner;
    private LocalDate lastReview;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}