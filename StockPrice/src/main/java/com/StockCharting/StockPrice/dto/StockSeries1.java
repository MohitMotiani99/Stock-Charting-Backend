package com.StockCharting.StockPrice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockSeries1 {

    private Double price;
    private LocalDate localDate;

}
