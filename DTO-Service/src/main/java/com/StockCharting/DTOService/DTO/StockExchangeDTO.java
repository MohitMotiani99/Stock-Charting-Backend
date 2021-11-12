package com.StockCharting.DTOService.DTO;

import java.util.UUID;

public class StockExchangeDTO {

    private String stockExchangeId = UUID.randomUUID().toString();
    private String stockExchangeName;
    private String brief;
    private Address contactAddress;
    private String remarks;
}
