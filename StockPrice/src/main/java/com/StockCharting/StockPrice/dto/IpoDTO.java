package com.StockCharting.StockPrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpoDTO {

    private String ipoId ;
    private String companyName;
    private String stockExchangeName;
    private Double pricePerShare;
    private Integer totalStocks;
    private LocalDate openDate;
    private String remarks;

}
