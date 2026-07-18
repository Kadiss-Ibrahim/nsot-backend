package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.dto.DeviceRoleRequestDTO;
import com.sielmed.nsotbackend.dto.DeviceRoleResponseDTO;
import com.sielmed.nsotbackend.entity.DeviceRole;
import com.sielmed.nsotbackend.exception.DuplicateResourceException;
import com.sielmed.nsotbackend.exception.ResourceInUseException;
import com.sielmed.nsotbackend.exception.ResourceNotFoundException;
import com.sielmed.nsotbackend.repository.DeviceRepository;
import com.sielmed.nsotbackend.repository.DeviceRoleRepository;
import com.sielmed.nsotbackend.service.DeviceRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceRoleServiceImpl implements DeviceRoleService {

    private final DeviceRoleRepository deviceRoleRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public List<DeviceRoleResponseDTO> findAll() {
        return deviceRoleRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public DeviceRoleResponseDTO findById(Long id) {
        DeviceRole deviceRole = deviceRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DeviceRole", id));
        return toResponseDTO(deviceRole);
    }

    @Override
    public DeviceRoleResponseDTO create(DeviceRoleRequestDTO requestDTO) {
        if (deviceRoleRepository.existsByNom(requestDTO.getNom())) {
            throw new DuplicateResourceException(
                    "Un rôle avec ce nom existe déjà : " + requestDTO.getNom());
        }
        DeviceRole deviceRole = new DeviceRole();
        deviceRole.setNom(requestDTO.getNom());
        return toResponseDTO(deviceRoleRepository.save(deviceRole));
    }

    @Override
    public DeviceRoleResponseDTO update(Long id, DeviceRoleRequestDTO requestDTO) {
        DeviceRole existing = deviceRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DeviceRole", id));
        existing.setNom(requestDTO.getNom());
        return toResponseDTO(deviceRoleRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!deviceRoleRepository.existsById(id)) {
            throw new ResourceNotFoundException("DeviceRole", id);
        }
        if (deviceRepository.existsByDeviceRoleId(id)) {
            throw new ResourceInUseException(
                    "Impossible de supprimer ce rôle : il est encore utilisé par au moins un device."
            );
        }
        deviceRoleRepository.deleteById(id);
    }

    private DeviceRoleResponseDTO toResponseDTO(DeviceRole deviceRole) {
        return new DeviceRoleResponseDTO(deviceRole.getId(), deviceRole.getNom());
    }
}