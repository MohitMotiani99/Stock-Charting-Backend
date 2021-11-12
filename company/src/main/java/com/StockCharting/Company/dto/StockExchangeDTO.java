package com.StockCharting.Company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockExchangeDTO {

    private String stockExchangeId;
    private String stockExchangeName;
    private String brief;
    private Address contactAddress;
    private String remarks;
}
