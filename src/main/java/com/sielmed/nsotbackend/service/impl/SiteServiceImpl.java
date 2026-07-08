package com.sielmed.nsotbackend.service.impl;

import com.sielmed.nsotbackend.entity.Site;
import com.sielmed.nsotbackend.exception.ResourceNotFoundException;
import com.sielmed.nsotbackend.repository.SiteRepository;
import com.sielmed.nsotbackend.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    @Override
    public List<Site> findAll() {
        return siteRepository.findAll();
    }

    @Override
    public Site findById(Long id) {
        return siteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Site", id));
    }

    @Override
    public Site create(Site site) {
        return siteRepository.save(site);
    }

    @Override
    public Site update(Long id, Site updated) {
        Site existing = findById(id);
        existing.setNom(updated.getNom());
        existing.setVille(updated.getVille());
        existing.setPays(updated.getPays());
        existing.setResponsable(updated.getResponsable());
        return siteRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!siteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Site", id);
        }
        siteRepository.deleteById(id);
    }
}