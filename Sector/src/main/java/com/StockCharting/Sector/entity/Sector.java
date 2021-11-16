package com.StockCharting.Sector.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;



    private String sectorId = UUID.randomUUID().toString();
    private String sectorName;
    private String brief;

}
