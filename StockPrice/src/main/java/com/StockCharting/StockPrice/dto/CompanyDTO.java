package com.StockCharting.StockPrice.dto;

import lombok.Data;

import java.util.Map;

@Data
public class CompanyDTO {

    private String companyId ;
    private String companyName;
    private Long turnover;
    private String ceo;
    private String[] boardOfDirectors;
    private Boolean listedInStockExchange;
    private String sector;
    private String brief;
    private Map<String,String> stockExchangeCodes;
}
