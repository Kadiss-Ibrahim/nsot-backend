package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.dto.SiteRequestDTO;
import com.sielmed.nsotbackend.dto.SiteResponseDTO;
import com.sielmed.nsotbackend.entity.Site;
import com.sielmed.nsotbackend.exception.ResourceInUseException;
import com.sielmed.nsotbackend.exception.ResourceNotFoundException;
import com.sielmed.nsotbackend.repository.DeviceRepository;
import com.sielmed.nsotbackend.repository.SiteRepository;
import com.sielmed.nsotbackend.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;
    private final DeviceRepository deviceRepository;

    @Override
    public List<SiteResponseDTO> findAll() {
        return siteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public SiteResponseDTO findById(Long id) {
        Site site = siteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Site", id));
        return toResponseDTO(site);
    }

    @Override
    public SiteResponseDTO create(SiteRequestDTO requestDTO) {
        Site site = new Site();
        site.setNom(requestDTO.getNom());
        site.setVille(requestDTO.getVille());
        site.setPays(requestDTO.getPays());
        site.setResponsable(requestDTO.getResponsable());
        return toResponseDTO(siteRepository.save(site));
    }

    @Override
    public SiteResponseDTO update(Long id, SiteRequestDTO requestDTO) {
        Site existing = siteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Site", id));
        existing.setNom(requestDTO.getNom());
        existing.setVille(requestDTO.getVille());
        existing.setPays(requestDTO.getPays());
        existing.setResponsable(requestDTO.getResponsable());
        return toResponseDTO(siteRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!siteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Site", id);
        }
        if (deviceRepository.existsBySiteId(id)) {
            throw new ResourceInUseException(
                    "Impossible de supprimer ce site : il est encore utilisé par au moins un device."
            );
        }
        siteRepository.deleteById(id);
    }

    private SiteResponseDTO toResponseDTO(Site site) {
        return new SiteResponseDTO(site.getId(), site.getNom(), site.getVille(), site.getPays(), site.getResponsable());
    }
}