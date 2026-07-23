package com.sielmed.nsotbackend.repository;

import com.sielmed.nsotbackend.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {
    @Query("SELECT s FROM Site s WHERE " +
            "(:nom IS NULL OR LOWER(s.nom) LIKE LOWER(CONCAT('%',CAST(:nom AS string), '%'))) AND " +
            "(:ville IS NULL OR LOWER(s.ville) LIKE LOWER(CONCAT('%',CAST(:ville AS string), '%')))")
    List<Site> search(@Param("nom") String nom, @Param("ville") String ville);
}
