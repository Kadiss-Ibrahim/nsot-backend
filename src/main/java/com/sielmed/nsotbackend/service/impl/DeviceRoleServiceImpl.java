package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.entity.DeviceRole;
import com.sielmed.nsotbackend.exception.DuplicateResourceException;
import com.sielmed.nsotbackend.exception.ResourceNotFoundException;
import com.sielmed.nsotbackend.repository.DeviceRoleRepository;
import com.sielmed.nsotbackend.service.DeviceRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceRoleServiceImpl implements DeviceRoleService {

    private final DeviceRoleRepository deviceRoleRepository;

    @Override
    public List<DeviceRole> findAll() {
        return deviceRoleRepository.findAll();
    }

    @Override
    public DeviceRole findById(Long id) {
        return deviceRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("DeviceRole", id));
    }

    @Override
    public DeviceRole create(DeviceRole deviceRole) {
        if (deviceRoleRepository.existsByNom(deviceRole.getNom())) {
            throw new DuplicateResourceException(
                    "Un rôle avec ce nom existe déjà : " + deviceRole.getNom());
        }
        return deviceRoleRepository.save(deviceRole);
    }

    @Override
    public DeviceRole update(Long id, DeviceRole updated) {
        DeviceRole existing = findById(id);
        existing.setNom(updated.getNom());
        return deviceRoleRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!deviceRoleRepository.existsById(id)) {
            throw new ResourceNotFoundException("DeviceRole", id);
        }
        deviceRoleRepository.deleteById(id);
    }
}