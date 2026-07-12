package com.sielmed.nsotbackend.controller;

import com.sielmed.nsotbackend.dto.DeviceRoleRequestDTO;
import com.sielmed.nsotbackend.dto.DeviceRoleResponseDTO;
import com.sielmed.nsotbackend.service.DeviceRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/device-roles")
@RequiredArgsConstructor
public class DeviceRoleController {

    private final DeviceRoleService deviceRoleService;

    @GetMapping
    public ResponseEntity<List<DeviceRoleResponseDTO>> findAll() {
        return ResponseEntity.ok(deviceRoleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceRoleResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceRoleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DeviceRoleResponseDTO> create(@Valid @RequestBody DeviceRoleRequestDTO dto) {
        DeviceRoleResponseDTO created = deviceRoleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeviceRoleResponseDTO> update(@PathVariable Long id,
                                                        @Valid @RequestBody DeviceRoleRequestDTO dto) {
        return ResponseEntity.ok(deviceRoleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        deviceRoleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}