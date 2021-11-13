package com.StockCharting.IPO.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ipo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ipoId = UUID.randomUUID().toString();
    private String companyName;
    private String stockExchangeName;
    private Double pricePerShare;
    private Integer totalStocks;
    private LocalDate openDate;
    private String remarks;

}
