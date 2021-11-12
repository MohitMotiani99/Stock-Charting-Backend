package com.StockCharting.IPO.service;

import com.StockCharting.IPO.dto.IpoDTO;
import com.StockCharting.IPO.entity.Ipo;
import com.StockCharting.IPO.exception.CompanyNotFoundException;
import com.StockCharting.IPO.exception.IpoNotFoundException;
import com.StockCharting.IPO.exception.StockExchangeNotFoundException;

public interface IpoService {
    IpoDTO saveIpo(IpoDTO ipoDTO) throws CompanyNotFoundException, StockExchangeNotFoundException;

    IpoDTO findIpoById(String ipoId) throws IpoNotFoundException;

    IpoDTO updateIpo(String ipoId, IpoDTO newFields) throws IpoNotFoundException, CompanyNotFoundException, StockExchangeNotFoundException;

    void deleteIpo(String ipoId) throws IpoNotFoundException;
}
