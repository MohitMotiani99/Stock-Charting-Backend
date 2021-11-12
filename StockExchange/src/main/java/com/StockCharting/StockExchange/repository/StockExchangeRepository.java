package com.StockCharting.StockExchange.repository;

import com.StockCharting.StockExchange.entity.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockExchangeRepository extends JpaRepository<StockExchange,Long> {
    Optional<StockExchange> findByStockExchangeNameIgnoreCase(String stockExchangeName);

    Optional<StockExchange> findByStockExchangeId(String stockExchangeId);
}
