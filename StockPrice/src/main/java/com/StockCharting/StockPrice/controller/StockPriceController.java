package com.StockCharting.StockPrice.controller;

import com.StockCharting.StockPrice.dto.ChartResponse;
import com.StockCharting.StockPrice.dto.StockPriceDTO;
import com.StockCharting.StockPrice.exception.CompanyNotFoundException;
import com.StockCharting.StockPrice.exception.FieldNotFoundException;
import com.StockCharting.StockPrice.exception.SectorNotFoundException;
import com.StockCharting.StockPrice.exception.StockExchangeNotFoundException;
import com.StockCharting.StockPrice.service.StockPriceService;
import com.StockCharting.StockPrice.service.StockPriceService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/prices")
@CrossOrigin
public class StockPriceController {

    @Autowired
    private StockPriceService stockPriceService;

    @Autowired
    private StockPriceService2 stockPriceService2;

    @PostMapping("/upload/{stockExchangeName}/{companyName}")
    public ResponseEntity<?> uploadPriceSheet(@RequestParam("file") MultipartFile file,@PathVariable("stockExchangeName") String stockExchangeName,@PathVariable("companyName") String companyName) throws IOException, StockExchangeNotFoundException, CompanyNotFoundException {
        List<StockPriceDTO> stockPriceDTOList = stockPriceService.uploadPriceSheet(file,stockExchangeName,companyName);
        return ResponseEntity.status(HttpStatus.OK)
                                .body(stockPriceDTOList);
    }

    @GetMapping("/company/stock-variations/{stockExchangeName}/{companyCode}")
    public ResponseEntity<?> getStockPricesOvertimeForACompany(@PathVariable("stockExchangeName") String stockExchangeName,@PathVariable("companyCode") String companyCode,@RequestParam String start,@RequestParam String end) throws StockExchangeNotFoundException, CompanyNotFoundException {

        ChartResponse chartResponse = stockPriceService.getStockPricesOvertimeForACompany(stockExchangeName,companyCode,start,end);
        return ResponseEntity.ok(chartResponse);
    }

    @GetMapping("/company/stock-variations/{sectorName}")
    public ResponseEntity<?> getStockPricesOvertimeForSector(@PathVariable("sectorName") String sectorName, @RequestParam String start, @RequestParam String end){

        ChartResponse chartResponse = stockPriceService.getStockPricesOvertimeForSector(sectorName,start,end);
        return ResponseEntity.ok(chartResponse);
    }

    @GetMapping("/stock-exchange/stock-variations/{stockExchangeName}")
    public ResponseEntity<?> getStockPricesOvertimeForStockExchange(@PathVariable("stockExchangeName") String stockExchangeName, @RequestParam String start, @RequestParam String end) throws StockExchangeNotFoundException {
        ChartResponse chartResponse = stockPriceService.getStockPricesOvertimeForStockExchange(stockExchangeName,start,end);
        return ResponseEntity.ok(chartResponse);
    }

    @GetMapping("/sector/stock-variations/{sectorName}/{companyName}")
    public ResponseEntity<?> getStockPricesOvertimeForStockExchange(@PathVariable("sectorName") String sectorName,@PathVariable("companyName") String companyName, @RequestParam String start, @RequestParam String end) throws CompanyNotFoundException, SectorNotFoundException {
        ChartResponse chartResponse = stockPriceService.getStockPricesOvertimeForASector(sectorName,companyName,start,end);
        return ResponseEntity.ok(chartResponse);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveStockPrice(@RequestBody StockPriceDTO stockPriceDTO) throws StockExchangeNotFoundException, CompanyNotFoundException, FieldNotFoundException {
        return ResponseEntity.ok(stockPriceService.saveStockPrice(stockPriceDTO));
    }

}
