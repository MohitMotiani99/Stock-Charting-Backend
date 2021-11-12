package com.StockCharting.StockExchange.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String stockExchangeId = UUID.randomUUID().toString();
    private String stockExchangeName;
    private String brief;

    @OneToOne(targetEntity = Address.class,cascade = CascadeType.ALL)
    private Address contactAddress;
    private String remarks;


}
