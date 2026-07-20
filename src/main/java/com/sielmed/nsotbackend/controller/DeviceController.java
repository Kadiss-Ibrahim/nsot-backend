package com.sielmed.nsotbackend.controller;

import com.sielmed.nsotbackend.dto.DeviceRequestDTO;
import com.sielmed.nsotbackend.dto.DeviceResponseDTO;
import com.sielmed.nsotbackend.enums.Status;
import com.sielmed.nsotbackend.service.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @GetMapping
    public ResponseEntity<List<DeviceResponseDTO>> findAll() {
        return ResponseEntity.ok(deviceService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DeviceResponseDTO> create(@Valid @RequestBody DeviceRequestDTO dto) {
        DeviceResponseDTO created = deviceService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> update(@PathVariable Long id,
                                                    @Valid @RequestBody DeviceRequestDTO dto) {
        return ResponseEntity.ok(deviceService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deviceService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<DeviceResponseDTO>> search(
            @RequestParam(required = false) String hostname,
            @RequestParam(required = false) String managementIp,
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Long siteId,
            @RequestParam(required = false) Status status){
        return ResponseEntity.ok(deviceService.search(hostname, managementIp, serialNumber, model, siteId, status));
    }
}