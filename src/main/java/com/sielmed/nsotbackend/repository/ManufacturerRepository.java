package com.sielmed.nsotbackend.repository;

import com.sielmed.nsotbackend.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Optional<Manufacturer> findByNom(String nom);
    boolean existsByNom(String nom);

    @Query("SELECT m FROM Manufacturer m WHERE " +
            "(:nom IS NULL OR LOWER(m.nom) LIKE LOWER(CONCAT('%', CAST(:nom AS string), '%')))")
    List<Manufacturer> search(@Param("nom") String nom);
}
