package com.sielmed.nsotbackend.service.impl;

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
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device findById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device", id));
    }

    @Override
    public Device create(Device device) {
        if (deviceRepository.existsByHostname(device.getHostname())) {
            throw new DuplicateResourceException(
                    "Un device avec ce hostname existe déjà : " + device.getHostname());
        }

        Site site = siteService.findById(device.getSite().getId());
        DeviceRole role = deviceRoleService.findById(device.getDeviceRole().getId());
        Manufacturer manufacturer = manufacturerService.findById(device.getManufacturer().getId());

        device.setSite(site);
        device.setDeviceRole(role);
        device.setManufacturer(manufacturer);

        return deviceRepository.save(device);
    }

    @Override
    public Device update(Long id, Device updated) {
        Device existing = findById(id);

        existing.setHostname(updated.getHostname());
        existing.setModel(updated.getModel());
        existing.setSerialNumber(updated.getSerialNumber());
        existing.setManagementIp(updated.getManagementIp());
        existing.setOs(updated.getOs());
        existing.setCurrentVersion(updated.getCurrentVersion());
        existing.setRack(updated.getRack());
        existing.setRackPosition(updated.getRackPosition());
        existing.setStatus(updated.getStatus());
        existing.setCriticality(updated.getCriticality());
        existing.setOwner(updated.getOwner());
        existing.setLastReview(updated.getLastReview());

        if (updated.getSite() != null) {
            existing.setSite(siteService.findById(updated.getSite().getId()));
        }
        if (updated.getDeviceRole() != null) {
            existing.setDeviceRole(deviceRoleService.findById(updated.getDeviceRole().getId()));
        }
        if (updated.getManufacturer() != null) {
            existing.setManufacturer(manufacturerService.findById(updated.getManufacturer().getId()));
        }

        return deviceRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Device", id);
        }
        deviceRepository.deleteById(id);
    }

    @Override
    public List<Device> search(String hostname, Long siteId, Status status) {
        return deviceRepository.search(hostname, siteId, status);
    }
}