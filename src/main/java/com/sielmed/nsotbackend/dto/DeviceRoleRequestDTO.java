package com.sielmed.nsotbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRoleRequestDTO {

    @NotBlank(message = "Le nom du rôle est obligatoire")
    private String nom;
}