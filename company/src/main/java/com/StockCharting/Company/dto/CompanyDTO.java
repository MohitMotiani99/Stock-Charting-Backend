package com.StockCharting.Company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
