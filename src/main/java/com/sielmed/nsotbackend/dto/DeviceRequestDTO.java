package com.sielmed.nsotbackend.dto;

import com.sielmed.nsotbackend.enums.Criticality;
import com.sielmed.nsotbackend.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRequestDTO {

    @NotBlank(message = "Le hostname est obligatoire")
    private String hostname;

    @NotNull(message = "Le site est obligatoire")
    private Long siteId;

    @NotNull(message = "Le rôle du device est obligatoire")
    private Long deviceRoleId;

    @NotNull(message = "Le manufacturer est obligatoire")
    private Long manufacturerId;

    private String model;
    private String serialNumber;
    private String managementIp;
    private String os;
    private String currentVersion;
    private String rack;
    private String rackPosition;

    @NotNull(message = "Le statut est obligatoire")
    private Status status;

    private Criticality criticality;
    private String owner;
    private LocalDate lastReview;
}