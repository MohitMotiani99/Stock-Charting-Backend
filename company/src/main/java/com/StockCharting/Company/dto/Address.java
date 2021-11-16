package com.StockCharting.Company.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String buildingNumber;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zip;

}
