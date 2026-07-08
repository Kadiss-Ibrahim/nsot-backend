package com.sielmed.nsotbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteRequestDTO {

    @NotBlank(message = "Le nom du site est obligatoire")
    private String nom;

    @NotBlank(message = "La ville est obligatoire")
    private String ville;

    @NotBlank(message = "Le pays est obligatoire")
    private String pays;

    private String responsable;
}