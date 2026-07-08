package com.sielmed.nsotbackend.service;

import com.sielmed.nsotbackend.entity.Site;

import java.util.List;

public interface SiteService {
    List<Site> findAll();
    Site findById(Long id);
    Site create(Site site);
    Site update(Long id, Site updated);
    void delete(Long id);
}