package com.sielmed.nsotbackend.repository;

import com.sielmed.nsotbackend.entity.Device;
import com.sielmed.nsotbackend.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findByHostname(String hostname);

    boolean existsByHostname(String hostname);
    boolean existsByManufacturerId(Long manufacturerId);
    boolean existsBySiteId(Long siteId);
    boolean existsByDeviceRoleId(Long deviceRoleId);

    List<Device> findBySiteId(Long siteId);

    List<Device> findByDeviceRoleId(Long deviceRoleId);

    List<Device> findByManufacturerId(Long manufacturerId);


    @Query("SELECT d FROM Device d WHERE " +
            "(:hostname IS NULL OR LOWER(d.hostname) LIKE LOWER(CONCAT('%', CAST(:hostname AS string), '%'))) AND " +
            "(:managementIp IS NULL OR LOWER(d.managementIp) LIKE LOWER(CONCAT('%', CAST(:managementIp AS string), '%'))) AND " +
            "(:serialNumber IS NULL OR LOWER(d.serialNumber) LIKE LOWER(CONCAT('%', CAST(:serialNumber AS string), '%'))) AND " +
            "(:model IS NULL OR LOWER(d.model) LIKE LOWER(CONCAT('%', CAST(:model AS string), '%'))) AND " +
            "(:siteId IS NULL OR d.site.id = :siteId) AND " +
            "(:status IS NULL OR d.status = :status)")
    List<Device> search(@Param("hostname") String hostname,
                        @Param("managementIp") String managementIp,
                        @Param("serialNumber") String serialNumber,
                        @Param("model") String model,
                        @Param("siteId") Long siteId,
                        @Param("status") Status status);

}
