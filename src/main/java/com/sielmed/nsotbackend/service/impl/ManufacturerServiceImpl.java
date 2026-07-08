package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.entity.Manufacturer;
import com.sielmed.nsotbackend.exception.DuplicateResourceException;
import com.sielmed.nsotbackend.exception.ResourceNotFoundException;
import com.sielmed.nsotbackend.repository.ManufacturerRepository;
import com.sielmed.nsotbackend.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer findById(Long id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Manufacturer", id));
    }

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        if (manufacturerRepository.existsByNom(manufacturer.getNom())) {
            throw new DuplicateResourceException(
                    "Un manufacturer avec ce nom existe déjà : " + manufacturer.getNom());
        }
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer update(Long id, Manufacturer updated) {
        Manufacturer existing = findById(id);
        existing.setNom(updated.getNom());
        return manufacturerRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!manufacturerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Manufacturer", id);
        }
        manufacturerRepository.deleteById(id);
    }
}