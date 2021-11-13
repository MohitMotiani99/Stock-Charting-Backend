package com.StockCharting.IPO.repository;

import com.StockCharting.IPO.entity.Ipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IpoRepository extends JpaRepository<Ipo,Long> {

    Optional<Ipo> findByIpoId(String ipoId);

    void deleteByIpoId(String ipoId);

    List<Ipo> findByStockExchangeNameAndCompanyName(String stockExchangeName,String companyName);
}
