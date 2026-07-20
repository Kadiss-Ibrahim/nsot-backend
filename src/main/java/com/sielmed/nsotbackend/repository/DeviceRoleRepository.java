package com.sielmed.nsotbackend.repository;

import com.sielmed.nsotbackend.entity.DeviceRole;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface DeviceRoleRepository extends JpaRepository<DeviceRole, Long> {
    Optional<DeviceRole> findByNom(String nom);
    boolean existsByNom(String nom);

    @Query("SELECT r FROM DeviceRole r WHERE " +
            "(:nom IS NULL OR LOWER(r.nom) LIKE LOWER(CONCAT('%',CAST(:nom AS string), '%')))")
    List<DeviceRole> search(@Param("nom") String nom);
}
