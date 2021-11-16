package com.StockCharting.Company.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String companyId = UUID.randomUUID().toString();
    private String companyName;
    private Long turnover;
    private String ceo;
    private String[] boardOfDirectors;
    private Boolean listedInStockExchange;
    private String sector;
    private String brief;

    @ElementCollection
    @CollectionTable(name = "CompanyCodes")
    @MapKeyColumn(name = "stock")
    @Column(name = "company_code")
    private Map<String,String> stockExchangeCodes;


}
