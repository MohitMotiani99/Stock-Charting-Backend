package com.StockCharting.StockPrice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class StockPriceDTO {

    private String stockPriceId;
    private String stockExchangeName;
    private String companyCode;
    private Double price;
    private LocalDate localDate;

}
