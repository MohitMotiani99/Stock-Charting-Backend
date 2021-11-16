package com.StockCharting.StockExchange.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.util.UUID;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String stockExchangeId = UUID.randomUUID().toString();
    private String stockExchangeName;
    private String brief;

    @OneToOne(targetEntity = Address.class,cascade = CascadeType.ALL)
    private Address contactAddress;
    private String remarks;


}
