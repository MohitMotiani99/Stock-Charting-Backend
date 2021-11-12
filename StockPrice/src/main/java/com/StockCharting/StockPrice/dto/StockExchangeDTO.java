package com.StockCharting.StockPrice.dto;

import lombok.Data;

@Data
public class StockExchangeDTO {

    private String stockExchangeId;
    private String stockExchangeName;
    private String brief;
    private Address contactAddress;
    private String remarks;
}
