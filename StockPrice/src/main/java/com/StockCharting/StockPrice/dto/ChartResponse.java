package com.StockCharting.StockPrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartResponse {
    private List<StockSeries1> stockSeries1List;
    private Double minPrice;
    private Double maxPrice;
    private Double avgPrice;
    private Double growth;
}
