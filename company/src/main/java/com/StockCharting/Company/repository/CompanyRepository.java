package com.StockCharting.Company.repository;

import com.StockCharting.Company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends MongoRepository<Company,String> {
    Optional<Company> findByCompanyNameIgnoreCase(String companyName);
    Optional<Company> findByCompanyId(String companyId);
}
