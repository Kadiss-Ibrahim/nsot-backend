package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.dto.DashboardStatsDTO;
import com.sielmed.nsotbackend.entity.Device;
import com.sielmed.nsotbackend.enums.Status;
import com.sielmed.nsotbackend.repository.DeviceRepository;
import com.sielmed.nsotbackend.repository.ManufacturerRepository;
import com.sielmed.nsotbackend.repository.SiteRepository;
import com.sielmed.nsotbackend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DeviceRepository deviceRepository;
    private final SiteRepository siteRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Override
    public DashboardStatsDTO getStats() {
        List<Device> devices = deviceRepository.findAll();

        long production = devices.stream().filter(d -> d.getStatus() == Status.PRODUCTION).count();
        long standby = devices.stream().filter(d -> d.getStatus() == Status.STANDBY).count();
        long critical = devices.stream().filter(d -> d.getStatus() == Status.CRITICAL).count();
        long decommissioned = devices.stream().filter(d -> d.getStatus() == Status.DECOMMISSIONED).count();

        Map<String, Long> bySite = devices.stream()
                .filter(d -> d.getSite() != null)
                .collect(Collectors.groupingBy(d -> d.getSite().getNom(), Collectors.counting()));

        Map<String, Long> byManufacturer = devices.stream()
                .filter(d -> d.getManufacturer() != null)
                .collect(Collectors.groupingBy(d -> d.getManufacturer().getNom(), Collectors.counting()));

        Map<String, Long> byRole = devices.stream()
                .filter(d -> d.getDeviceRole() != null)
                .collect(Collectors.groupingBy(d -> d.getDeviceRole().getNom(), Collectors.counting()));

        return new DashboardStatsDTO(
                devices.size(),
                siteRepository.count(),
                manufacturerRepository.count(),
                production,
                standby,
                critical,
                decommissioned,
                bySite,
                byManufacturer,
                byRole
        );
    }
}