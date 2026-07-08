package com.sielmed.nsotbackend.entity;

import com.sielmed.nsotbackend.enums.Criticality;
import com.sielmed.nsotbackend.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String hostname;

   @ManyToOne
   @JoinColumn(name="site_id", nullable=false)
   private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_role_id", nullable = false)
    private DeviceRole deviceRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    private String model;

    @Column(name = "serial_number", unique = true)
    private String serialNumber;

    @Column(name = "management_ip")
    private String managementIp;

    @Column(name = "os")
    private String os;

    @Column(name = "current_version")
    private String currentVersion;

    private String rack;

    @Column(name = "rack_position")
    private String rackPosition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Criticality criticality;

    private String owner;

    @Column(name = "last_review")
    private LocalDate lastReview;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}