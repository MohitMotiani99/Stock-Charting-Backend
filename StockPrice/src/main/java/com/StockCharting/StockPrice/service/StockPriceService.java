package com.StockCharting.StockPrice.service;

import com.StockCharting.StockPrice.dto.ChartResponse;
import com.StockCharting.StockPrice.dto.StockPriceDTO;
import com.StockCharting.StockPrice.exception.CompanyNotFoundException;
import com.StockCharting.StockPrice.exception.FieldNotFoundException;
import com.StockCharting.StockPrice.exception.SectorNotFoundException;
import com.StockCharting.StockPrice.exception.StockExchangeNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StockPriceService {
    List<StockPriceDTO> uploadPriceSheet(MultipartFile multipartFile,String stockExchangeName,String companyName) throws IOException, StockExchangeNotFoundException, CompanyNotFoundException;

    ChartResponse getStockPricesOvertimeForACompany(String stockExchangeName, String companyCode, String start, String end) throws StockExchangeNotFoundException, CompanyNotFoundException;

    ChartResponse getStockPricesOvertimeForSector(String sectorName,String start,String end);

    ChartResponse getStockPricesOvertimeForStockExchange(String stockExchangeName, String start, String end) throws StockExchangeNotFoundException;

    ChartResponse getStockPricesOvertimeForASector(String sectorName, String companyName, String start, String end) throws CompanyNotFoundException, SectorNotFoundException;

    StockPriceDTO saveStockPrice(StockPriceDTO stockPriceDTO) throws StockExchangeNotFoundException, CompanyNotFoundException, FieldNotFoundException;
}
