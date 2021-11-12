package com.StockCharting.StockPrice.service;

import com.StockCharting.StockPrice.dto.ChartResponse;
import com.StockCharting.StockPrice.exception.CompanyNotFoundException;
import com.StockCharting.StockPrice.exception.SectorNotFoundException;

public interface StockPriceService2 {
    ChartResponse getStockPricesOvertimeForASector(String sectorName, String companyName, String start, String end) throws CompanyNotFoundException, SectorNotFoundException;
}
