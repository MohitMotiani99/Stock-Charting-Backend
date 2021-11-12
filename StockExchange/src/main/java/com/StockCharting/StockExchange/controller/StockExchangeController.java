package com.StockCharting.StockExchange.controller;

import com.StockCharting.StockExchange.dto.StockExchangeDTO;
import com.StockCharting.StockExchange.exception.StockExchangeAlreadyExistsException;
import com.StockCharting.StockExchange.exception.StockExchangeNotFoundException;
import com.StockCharting.StockExchange.service.StockExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchanges")
@Slf4j
@CrossOrigin
public class StockExchangeController {

    @Autowired
    private StockExchangeService stockExchangeService;

    @PostMapping("/save")
    public ResponseEntity<?> saveStockExchange(@RequestBody StockExchangeDTO stockExchangeDTO) throws StockExchangeAlreadyExistsException {
        return ResponseEntity.ok(stockExchangeService.saveStockExchange(stockExchangeDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findStockExchangeById(@RequestParam String stockExchangeId) throws StockExchangeNotFoundException {
        return ResponseEntity.ok(stockExchangeService.findStockExchangeById(stockExchangeId));
    }

    @GetMapping("/searchByName")
    public ResponseEntity<?> findStockExchangeByName(@RequestParam String stockExchangeName) throws StockExchangeNotFoundException {
        return ResponseEntity.ok(stockExchangeService.findStockExchangeByName(stockExchangeName));
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllStockExchanges(){
        return ResponseEntity.ok(stockExchangeService.findAllStockExchanges());
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateStockExchange(@RequestParam String stockExchangeId,@RequestBody StockExchangeDTO newFields) throws StockExchangeNotFoundException, StockExchangeAlreadyExistsException {
        return ResponseEntity.ok(stockExchangeService.updateStockExchange(stockExchangeId,newFields));
    }


}
