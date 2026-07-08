package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.entity.Manufacturer;

import java.util.List;

public interface ManufacturerService {
    List<Manufacturer> findAll();
    Manufacturer findById(Long id);
    Manufacturer create(Manufacturer manufacturer);
    Manufacturer update(Long id, Manufacturer updated);
    void delete(Long id);
}