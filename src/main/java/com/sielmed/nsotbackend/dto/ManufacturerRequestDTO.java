package com.sielmed.nsotbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerRequestDTO {

    @NotBlank(message = "Le nom du manufacturer est obligatoire")
    private String nom;
}