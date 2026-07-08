package com.sielmed.nsotbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteResponseDTO {

    private Long id;
    private String nom;
    private String ville;
    private String pays;
    private String responsable;
}