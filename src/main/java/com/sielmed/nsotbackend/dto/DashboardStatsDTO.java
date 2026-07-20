package com.sielmed.nsotbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private long totalDevices;
    private long totalSites;
    private long totalManufacturers;
    private long productionCount;
    private long standbyCount;
    private long criticalCount;
    private long decommissionedCount;

    private Map<String, Long> devicesBySite;
    private Map<String, Long> devicesByManufacturer;
    private Map<String, Long> devicesByRole;
}