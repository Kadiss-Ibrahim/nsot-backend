package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.dto.DeviceRequestDTO;
import com.sielmed.nsotbackend.dto.DeviceResponseDTO;
import com.sielmed.nsotbackend.dto.DeviceRoleResponseDTO;
import com.sielmed.nsotbackend.dto.ManufacturerResponseDTO;
import com.sielmed.nsotbackend.dto.SiteResponseDTO;
import com.sielmed.nsotbackend.entity.Device;
import com.sielmed.nsotbackend.entity.DeviceRole;
import com.sielmed.nsotbackend.entity.Manufacturer;
import com.sielmed.nsotbackend.entity.Site;
import com.sielmed.nsotbackend.enums.Status;
import com.sielmed.nsotbackend.exception.DuplicateResourceException;
import com.sielmed.nsotbackend.exception.ResourceNotFoundException;
import com.sielmed.nsotbackend.repository.DeviceRepository;
import com.sielmed.nsotbackend.service.DeviceRoleService;
import com.sielmed.nsotbackend.service.DeviceService;
import com.sielmed.nsotbackend.service.ManufacturerService;
import com.sielmed.nsotbackend.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final SiteService siteService;
    private final DeviceRoleService deviceRoleService;
    private final ManufacturerService manufacturerService;

    @Override
    public List<DeviceResponseDTO> findAll() {
        return deviceRepository.findAll().stream()
                .map(this::toResponseDTO)
                        .toList();
    }

    @Override
    public DeviceResponseDTO findById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device", id));
        return toResponseDTO(device);
    }

    @Override
    public DeviceResponseDTO create(DeviceRequestDTO requestDTO) {
        if (deviceRepository.existsByHostname(requestDTO.getHostname())) {
            throw new DuplicateResourceException(
                    "Un device avec ce hostname existe déjà : " + requestDTO.getHostname());
        }

        Device device = new Device();
        device.setHostname(requestDTO.getHostname());
        device.setModel(requestDTO.getModel());
        device.setSerialNumber(requestDTO.getSerialNumber());
        device.setManagementIp(requestDTO.getManagementIp());
        device.setOs(requestDTO.getOs());
        device.setCurrentVersion(requestDTO.getCurrentVersion());
        device.setRack(requestDTO.getRack());
        device.setRackPosition(requestDTO.getRackPosition());
        device.setStatus(requestDTO.getStatus());
        device.setCriticality(requestDTO.getCriticality());
        device.setOwner(requestDTO.getOwner());
        device.setLastReview(requestDTO.getLastReview());

        device.setSite(toEntitySite(siteService.findById(requestDTO.getSiteId())));
        device.setDeviceRole(toEntityDeviceRole(deviceRoleService.findById(requestDTO.getDeviceRoleId())));
        device.setManufacturer(toEntityManufacturer(manufacturerService.findById(requestDTO.getManufacturerId())));

        return toResponseDTO(deviceRepository.save(device));
    }

    @Override
    public DeviceResponseDTO update(Long id, DeviceRequestDTO requestDTO) {
        Device existing = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device", id));

        existing.setHostname(requestDTO.getHostname());
        existing.setModel(requestDTO.getModel());
        existing.setSerialNumber(requestDTO.getSerialNumber());
        existing.setManagementIp(requestDTO.getManagementIp());
        existing.setOs(requestDTO.getOs());
        existing.setCurrentVersion(requestDTO.getCurrentVersion());
        existing.setRack(requestDTO.getRack());
        existing.setRackPosition(requestDTO.getRackPosition());
        existing.setStatus(requestDTO.getStatus());
        existing.setCriticality(requestDTO.getCriticality());
        existing.setOwner(requestDTO.getOwner());
        existing.setLastReview(requestDTO.getLastReview());

        if (requestDTO.getSiteId() != null) {
            existing.setSite(toEntitySite(siteService.findById(requestDTO.getSiteId())));
        }
        if (requestDTO.getDeviceRoleId() != null) {
            existing.setDeviceRole(toEntityDeviceRole(deviceRoleService.findById(requestDTO.getDeviceRoleId())));
        }
        if (requestDTO.getManufacturerId() != null) {
            existing.setManufacturer(toEntityManufacturer(manufacturerService.findById(requestDTO.getManufacturerId())));
        }

        return toResponseDTO(deviceRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device", id);
        }
        deviceRepository.deleteById(id);
    }

    @Override
    public List<DeviceResponseDTO> search(String hostname, Long siteId, Status status) {
        return deviceRepository.search(hostname, siteId, status).stream()
                .map(this::toResponseDTO)
                        .toList();
    }

    private DeviceResponseDTO toResponseDTO(Device device) {
        return new DeviceResponseDTO(
                device.getId(),
                device.getHostname(),
                toSiteResponseDTO(device.getSite()),
                toDeviceRoleResponseDTO(device.getDeviceRole()),
                toManufacturerResponseDTO(device.getManufacturer()),
                device.getModel(),
                device.getSerialNumber(),
                device.getManagementIp(),
                device.getOs(),
                device.getCurrentVersion(),
                device.getRack(),
                device.getRackPosition(),
                device.getStatus(),
                device.getCriticality(),
                device.getOwner(),
                device.getLastReview(),
                device.getCreatedAt(),
                device.getUpdatedAt()
        );
    }

    private SiteResponseDTO toSiteResponseDTO(Site site) {
        return site == null ? null : new SiteResponseDTO(site.getId(), site.getNom(), site.getVille(), site.getPays(), site.getResponsable());
    }

    private DeviceRoleResponseDTO toDeviceRoleResponseDTO(DeviceRole deviceRole) {
        return deviceRole == null ? null : new DeviceRoleResponseDTO(deviceRole.getId(), deviceRole.getNom());
    }

    private ManufacturerResponseDTO toManufacturerResponseDTO(Manufacturer manufacturer) {
        return manufacturer == null ? null : new ManufacturerResponseDTO(manufacturer.getId(), manufacturer.getNom());
    }

    private Site toEntitySite(SiteResponseDTO siteResponseDTO) {
        if (siteResponseDTO == null) {
            return null;
        }
        Site site = new Site();
        site.setId(siteResponseDTO.getId());
        site.setNom(siteResponseDTO.getNom());
        site.setVille(siteResponseDTO.getVille());
        site.setPays(siteResponseDTO.getPays());
        site.setResponsable(siteResponseDTO.getResponsable());
        return site;
    }

    private DeviceRole toEntityDeviceRole(DeviceRoleResponseDTO deviceRoleResponseDTO) {
        if (deviceRoleResponseDTO == null) {
            return null;
        }
        DeviceRole deviceRole = new DeviceRole();
        deviceRole.setId(deviceRoleResponseDTO.getId());
        deviceRole.setNom(deviceRoleResponseDTO.getNom());
        return deviceRole;
    }

    private Manufacturer toEntityManufacturer(ManufacturerResponseDTO manufacturerResponseDTO) {
        if (manufacturerResponseDTO == null) {
            return null;
        }
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturerResponseDTO.getId());
        manufacturer.setNom(manufacturerResponseDTO.getNom());
        return manufacturer;
    }
}