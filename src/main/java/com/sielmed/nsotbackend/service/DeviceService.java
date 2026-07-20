package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.dto.DeviceRequestDTO;
import com.sielmed.nsotbackend.dto.DeviceResponseDTO;
import com.sielmed.nsotbackend.enums.Status;

import java.util.List;

public interface DeviceService {
    List<DeviceResponseDTO> findAll();
    DeviceResponseDTO findById(Long id);
    DeviceResponseDTO create(DeviceRequestDTO requestDTO);
    DeviceResponseDTO update(Long id, DeviceRequestDTO requestDTO);
    void delete(Long id);
    List<DeviceResponseDTO> search(String hostname, String managementIp, String serialNumber, String model, Long siteId, Status status );
}