package com.sielmed.nsotbackend.repository;

import com.sielmed.nsotbackend.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByHostname(String hostname);

    boolean existsByHostname(String hostname);

    List<Device> findBySiteId(Long siteId);

    List<Device> findByDeviceRoleId(Long deviceRoleId);

    List<Device> findByManufacturerId(Long manufacturerId);

    // Recherche multi-critères simple (module "Devices + recherche" prévu semaine 4)
    @Query("SELECT d FROM Device d WHERE " +
            "(:hostname IS NULL OR LOWER(d.hostname) LIKE LOWER(CONCAT('%', :hostname, '%'))) AND " +
            "(:siteId IS NULL OR d.site.id = :siteId) AND " +
            "(:status IS NULL OR d.status = :status)")
    List<Device> search(@Param("hostname") String hostname,
                        @Param("siteId") Long siteId,
                        @Param("status") com.sielmed.nsotbackend.enums.Status status);

}
