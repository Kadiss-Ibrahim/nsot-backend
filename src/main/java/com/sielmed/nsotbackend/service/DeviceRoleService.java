package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.dto.DeviceRoleRequestDTO;
import com.sielmed.nsotbackend.dto.DeviceRoleResponseDTO;

import java.util.List;

public interface DeviceRoleService {
    List<DeviceRoleResponseDTO> findAll();
    DeviceRoleResponseDTO findById(Long id);
    DeviceRoleResponseDTO create(DeviceRoleRequestDTO requestDTO);
    DeviceRoleResponseDTO update(Long id, DeviceRoleRequestDTO requestDTO);
    void delete(Long id);
}