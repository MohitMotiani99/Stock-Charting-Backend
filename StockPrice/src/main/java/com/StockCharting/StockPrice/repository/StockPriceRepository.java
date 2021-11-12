package com.StockCharting.StockPrice.repository;

import com.StockCharting.StockPrice.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockPriceRepository extends JpaRepository<StockPrice,Long> {
}
