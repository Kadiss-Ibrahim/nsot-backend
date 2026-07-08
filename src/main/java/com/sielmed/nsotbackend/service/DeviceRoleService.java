package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.entity.DeviceRole;

import java.util.List;

public interface DeviceRoleService {
    List<DeviceRole> findAll();
    DeviceRole findById(Long id);
    DeviceRole create(DeviceRole deviceRole);
    DeviceRole update(Long id, DeviceRole updated);
    void delete(Long id);
}