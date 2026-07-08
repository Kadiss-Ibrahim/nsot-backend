package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.dto.SiteRequestDTO;
import com.sielmed.nsotbackend.dto.SiteResponseDTO;

import java.util.List;

public interface SiteService {
    List<SiteResponseDTO> findAll();
    SiteResponseDTO findById(Long id);
    SiteResponseDTO create(SiteRequestDTO requestDTO);
    SiteResponseDTO update(Long id, SiteRequestDTO requestDTO);
    void delete(Long id);
}