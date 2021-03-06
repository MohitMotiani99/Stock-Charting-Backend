package com.StockCharting.StockPrice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockPriceDTO {

    private String stockPriceId;
    private String stockExchangeName;
    private String companyCode;
    private Double price;
    private LocalDate localDate;

}
