package com.sielmed.nsotbackend.repository;

import com.sielmed.nsotbackend.entity.DeviceRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRoleRepository extends JpaRepository<DeviceRole, Long> {
    Optional<DeviceRole> findByNom(String nom);
    boolean existsByNom(String nom);

}
