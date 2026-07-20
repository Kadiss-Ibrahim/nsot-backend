package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.dto.ManufacturerRequestDTO;
import com.sielmed.nsotbackend.dto.ManufacturerResponseDTO;
import com.sielmed.nsotbackend.entity.Manufacturer;
import com.sielmed.nsotbackend.exception.DuplicateResourceException;
import com.sielmed.nsotbackend.exception.ResourceInUseException;
import com.sielmed.nsotbackend.exception.ResourceNotFoundException;
import com.sielmed.nsotbackend.repository.DeviceRepository;
import com.sielmed.nsotbackend.repository.ManufacturerRepository;
import com.sielmed.nsotbackend.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public List<ManufacturerResponseDTO> findAll() {
        return manufacturerRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public ManufacturerResponseDTO findById(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", id));
        return toResponseDTO(manufacturer);
    }

    @Override
    public ManufacturerResponseDTO create(ManufacturerRequestDTO requestDTO) {
        if (manufacturerRepository.existsByNom(requestDTO.getNom())) {
            throw new DuplicateResourceException(
                    "Un manufacturer avec ce nom existe déjà : " + requestDTO.getNom());
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setNom(requestDTO.getNom());
        return toResponseDTO(manufacturerRepository.save(manufacturer));
    }

    @Override
    public ManufacturerResponseDTO update(Long id, ManufacturerRequestDTO requestDTO) {
        Manufacturer existing = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", id));
        existing.setNom(requestDTO.getNom());
        return toResponseDTO(manufacturerRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!manufacturerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Manufacturer", id);
        }
        if (deviceRepository.existsByManufacturerId(id)) {
            throw new ResourceInUseException(
                    "Impossible de supprimer ce manufacturer : il est encore utilisé par au moins un device."
            );
        }
        manufacturerRepository.deleteById(id);
    }

    @Override
    public List<ManufacturerResponseDTO> search(String nom) {
        return manufacturerRepository.search(nom)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private ManufacturerResponseDTO toResponseDTO(Manufacturer manufacturer) {
        return new ManufacturerResponseDTO(manufacturer.getId(), manufacturer.getNom());
    }
}