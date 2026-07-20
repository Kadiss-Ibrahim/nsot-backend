package com.sielmed.nsotbackend.controller;

import com.sielmed.nsotbackend.dto.SiteRequestDTO;
import com.sielmed.nsotbackend.dto.SiteResponseDTO;
import com.sielmed.nsotbackend.service.SiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sites")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    @GetMapping
    public ResponseEntity<List<SiteResponseDTO>> findAll() {
        return ResponseEntity.ok(siteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(siteService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SiteResponseDTO> create(@Valid @RequestBody SiteRequestDTO dto) {
        SiteResponseDTO created = siteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteResponseDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody SiteRequestDTO dto) {
        return ResponseEntity.ok(siteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        siteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SiteResponseDTO>> search(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String ville) {
        return ResponseEntity.ok(siteService.search(nom, ville));
    }
}