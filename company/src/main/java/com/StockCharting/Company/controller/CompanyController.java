package com.StockCharting.Company.controller;

import com.StockCharting.Company.dto.CompanyDTO;
import com.StockCharting.Company.exception.CompanyAlreadyExistsException;
import com.StockCharting.Company.exception.CompanyNotFoundException;
import com.StockCharting.Company.exception.SectorNotFoundException;
import com.StockCharting.Company.exception.StockExchangeNotFoundException;
import com.StockCharting.Company.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
@Slf4j
@CrossOrigin
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCompany(@RequestBody CompanyDTO companyDTO) throws StockExchangeNotFoundException, SectorNotFoundException, CompanyAlreadyExistsException, CompanyNotFoundException {
        log.info("Inside saveCompany of CompanyController");
        return ResponseEntity.ok(companyService.saveCompany(companyDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findCompanyById(@RequestParam String companyId) throws CompanyNotFoundException {
        log.info("Inside findCompanyById of CompanyController");
        return ResponseEntity.ok(companyService.findCompanyById(companyId));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<?> findCompanyByName(@RequestParam String companyName) throws CompanyNotFoundException {
        log.info("Inside findCompanyByName of CompanyController");
        return ResponseEntity.ok(companyService.findCompanyByName(companyName));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCompany(@RequestParam String companyId ,@RequestBody CompanyDTO newCompany) throws CompanyNotFoundException, StockExchangeNotFoundException, SectorNotFoundException, CompanyAlreadyExistsException {
        log.info("Inside updateCompany of CompanyController");
        return ResponseEntity.ok(companyService.updateCompany(companyId,newCompany));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllCompanies(){
        log.info("Inside findAllCompanies of CompanyController");
        return ResponseEntity.ok(companyService.findAllCompanies());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCompany(@RequestParam String companyId) throws CompanyNotFoundException {
        log.info("Inside deleteCompany of CompanyController");
        companyService.deleteCompany(companyId);
        return ResponseEntity.status(HttpStatus.SC_NO_CONTENT).body("Company "+companyId+" Deleted");
    }
}
