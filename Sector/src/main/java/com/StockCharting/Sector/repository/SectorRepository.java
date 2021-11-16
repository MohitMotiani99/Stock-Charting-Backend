package com.StockCharting.Sector.repository;

import com.StockCharting.Sector.entity.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SectorRepository extends MongoRepository<Sector,String> {
    Optional<Sector> findBySectorNameIgnoreCase(String sectorName);

    Optional<Sector> findBySectorId(String sectorId);

    void deleteBySectorId(String sectorId);
}
