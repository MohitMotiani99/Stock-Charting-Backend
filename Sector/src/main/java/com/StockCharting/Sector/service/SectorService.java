package com.StockCharting.Sector.service;

import com.StockCharting.Sector.dto.SectorDTO;
import com.StockCharting.Sector.exception.SectorAlreadyExistsException;
import com.StockCharting.Sector.exception.SectorNotFoundException;

import java.util.List;

public interface SectorService {

    SectorDTO saveSector(SectorDTO sectorDTO) throws SectorAlreadyExistsException;

    SectorDTO findSectorByName(String sectorName) throws SectorNotFoundException;

    SectorDTO findSectorById(String sectorId) throws SectorNotFoundException;

    SectorDTO updateSector(String sectorId, SectorDTO newFields) throws SectorNotFoundException, SectorAlreadyExistsException;

    void deleteSector(String sectorId) throws SectorNotFoundException;

    List<SectorDTO> getAllSectors();
}
