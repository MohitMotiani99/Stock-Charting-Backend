package com.StockCharting.IPO.repository;

import com.StockCharting.IPO.entity.Ipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IpoRepository extends JpaRepository<Ipo,Long> {

    Optional<Ipo> findByIpoId(String ipoId);

    void deleteByIpoId(String ipoId);
}
