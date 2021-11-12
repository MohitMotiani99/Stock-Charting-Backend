package com.StockCharting.Sector.controller;

import com.StockCharting.Sector.dto.SectorDTO;
import com.StockCharting.Sector.exception.SectorAlreadyExistsException;
import com.StockCharting.Sector.exception.SectorNotFoundException;
import com.StockCharting.Sector.service.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sectors")
@CrossOrigin
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @PostMapping("/save")
    public ResponseEntity<?> saveSector(@RequestBody SectorDTO sectorDTO) throws SectorAlreadyExistsException {
        return ResponseEntity.ok(sectorService.saveSector(sectorDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findSectorById(@RequestParam String sectorId) throws SectorNotFoundException {
        return ResponseEntity.ok(sectorService.findSectorById(sectorId));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<?> findSectorByName(@RequestParam String sectorName) throws SectorNotFoundException {
        return ResponseEntity.ok(sectorService.findSectorByName(sectorName));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSector(@RequestParam String sectorId,@RequestBody SectorDTO newFields) throws SectorNotFoundException, SectorAlreadyExistsException {
        return ResponseEntity.ok(sectorService.updateSector(sectorId,newFields));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteSector(@RequestParam String sectorId) throws SectorNotFoundException {
        sectorService.deleteSector(sectorId);
        return (ResponseEntity<?>) ResponseEntity.noContent();
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllSectors(){
        return ResponseEntity.ok(sectorService.getAllSectors());
    }
}
