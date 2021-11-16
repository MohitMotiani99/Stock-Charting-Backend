package com.StockCharting.StockExchange.repository;

import com.StockCharting.StockExchange.entity.StockExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface StockExchangeRepository extends MongoRepository<StockExchange,String> {
    Optional<StockExchange> findByStockExchangeNameIgnoreCase(String stockExchangeName);

    Optional<StockExchange> findByStockExchangeId(String stockExchangeId);
}
