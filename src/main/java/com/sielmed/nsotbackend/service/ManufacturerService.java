package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.dto.ManufacturerRequestDTO;
import com.sielmed.nsotbackend.dto.ManufacturerResponseDTO;

import java.util.List;

public interface ManufacturerService {
    List<ManufacturerResponseDTO> findAll();
    ManufacturerResponseDTO findById(Long id);
    ManufacturerResponseDTO create(ManufacturerRequestDTO requestDTO);
    ManufacturerResponseDTO update(Long id, ManufacturerRequestDTO requestDTO);
    void delete(Long id);
}