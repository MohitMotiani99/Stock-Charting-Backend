package com.StockCharting.Company.repository;

import com.StockCharting.Company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Long> {
    Optional<Company> findByCompanyNameIgnoreCase(String companyName);
    Optional<Company> findByCompanyId(String companyId);
}
