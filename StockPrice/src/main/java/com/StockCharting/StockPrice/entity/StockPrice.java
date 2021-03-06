package com.StockCharting.StockPrice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StockPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String stockPriceId = UUID.randomUUID().toString();
    private String stockExchangeName;
    private String companyCode;
    private Double price;
    private LocalDate localDate;

}
