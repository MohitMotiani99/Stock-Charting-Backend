package com.StockCharting.StockPrice.repository;

import com.StockCharting.StockPrice.entity.StockPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockPriceRepository extends MongoRepository<StockPrice,String> {
}
