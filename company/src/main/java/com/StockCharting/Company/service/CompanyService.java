package com.StockCharting.Company.service;

import com.StockCharting.Company.dto.CompanyDTO;
import com.StockCharting.Company.exception.CompanyAlreadyExistsException;
import com.StockCharting.Company.exception.CompanyNotFoundException;
import com.StockCharting.Company.exception.SectorNotFoundException;
import com.StockCharting.Company.exception.StockExchangeNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompanyService {

    CompanyDTO saveCompany(CompanyDTO company) throws StockExchangeNotFoundException, SectorNotFoundException, CompanyAlreadyExistsException, CompanyNotFoundException;

    CompanyDTO findCompanyById(String companyId) throws CompanyNotFoundException;

    CompanyDTO findCompanyByName(String companyName) throws CompanyNotFoundException;

    CompanyDTO updateCompany(String companyId, CompanyDTO newCompany) throws CompanyNotFoundException, StockExchangeNotFoundException, SectorNotFoundException, CompanyAlreadyExistsException;

    List<CompanyDTO> findAllCompanies();

    void deleteCompany(String companyId) throws CompanyNotFoundException;
}
