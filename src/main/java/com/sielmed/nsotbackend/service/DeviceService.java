package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.entity.Device;
import com.sielmed.nsotbackend.enums.Status;

import java.util.List;

public interface DeviceService {
    List<Device> findAll();
    Device findById(Long id);
    Device create(Device device);
    Device update(Long id, Device updated);
    void delete(Long id);
    List<Device> search(String hostname, Long siteId, Status status);
}