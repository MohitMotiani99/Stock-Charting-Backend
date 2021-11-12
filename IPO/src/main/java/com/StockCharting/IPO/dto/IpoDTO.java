package com.StockCharting.IPO.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IpoDTO {

    private String ipoId ;
    private String companyName;
    private String stockExchangeName;
    private Double pricePerShare;
    private Integer totalStocks;
    private LocalDateTime openDateTime;
    private String remarks;

}
