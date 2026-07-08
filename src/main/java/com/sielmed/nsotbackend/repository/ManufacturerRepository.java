package com.sielmed.nsotbackend.repository;

import com.sielmed.nsotbackend.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Optional<Manufacturer> findByNom(String nom);
    boolean existsByNom(String nom);
}
