package com.sielmed.nsotbackend.controller;

import com.sielmed.nsotbackend.dto.ManufacturerRequestDTO;
import com.sielmed.nsotbackend.dto.ManufacturerResponseDTO;
import com.sielmed.nsotbackend.service.ManufacturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manufacturers")
@RequiredArgsConstructor
public class ManufacturerController {

    private final ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<ManufacturerResponseDTO>> findAll() {
        return ResponseEntity.ok(manufacturerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(manufacturerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ManufacturerResponseDTO> create(@Valid @RequestBody ManufacturerRequestDTO dto) {
        ManufacturerResponseDTO created = manufacturerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDTO> update (@PathVariable Long id,
                                                            @Valid @RequestBody ManufacturerRequestDTO dto) {
        ManufacturerResponseDTO updated = manufacturerService.update(id,dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDTO> delete (@PathVariable Long id) {
        manufacturerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ManufacturerResponseDTO>> search(
            @RequestParam(required = false) String nom){
        return ResponseEntity.ok(manufacturerService.search(nom));
    }

}
