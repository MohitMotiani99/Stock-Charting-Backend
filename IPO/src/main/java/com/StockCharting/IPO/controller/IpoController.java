package com.StockCharting.IPO.controller;

import com.StockCharting.IPO.dto.IpoDTO;
import com.StockCharting.IPO.exception.CompanyNotFoundException;
import com.StockCharting.IPO.exception.IpoNotFoundException;
import com.StockCharting.IPO.exception.StockExchangeNotFoundException;
import com.StockCharting.IPO.service.IpoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ipos")
public class IpoController {

    @Autowired
    private IpoService ipoService;

    @PostMapping("/save")
    public ResponseEntity<?> saveIpo(@RequestBody IpoDTO ipoDTO) throws CompanyNotFoundException, StockExchangeNotFoundException {
        return ResponseEntity.ok(ipoService.saveIpo(ipoDTO));
    }

    @GetMapping("/search")
    public ResponseEntity<?> findIpoById(@RequestParam String ipoId) throws IpoNotFoundException {
        return ResponseEntity.ok(ipoService.findIpoById(ipoId));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateIpo(@RequestParam String ipoId,@RequestBody IpoDTO newFields) throws IpoNotFoundException, CompanyNotFoundException, StockExchangeNotFoundException {
        return ResponseEntity.ok(ipoService.updateIpo(ipoId,newFields));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteIpo(@RequestParam String ipoId) throws IpoNotFoundException {
        ipoService.deleteIpo(ipoId);
        return (ResponseEntity<?>) ResponseEntity.noContent();
    }

}
