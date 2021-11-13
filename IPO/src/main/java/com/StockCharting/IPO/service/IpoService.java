package com.StockCharting.IPO.service;

import com.StockCharting.IPO.dto.IpoDTO;
import com.StockCharting.IPO.entity.Ipo;
import com.StockCharting.IPO.exception.CompanyNotFoundException;
import com.StockCharting.IPO.exception.FieldNotFoundException;
import com.StockCharting.IPO.exception.IpoNotFoundException;
import com.StockCharting.IPO.exception.StockExchangeNotFoundException;

import java.util.List;

public interface IpoService {
    IpoDTO saveIpo(IpoDTO ipoDTO) throws CompanyNotFoundException, StockExchangeNotFoundException, FieldNotFoundException;

    IpoDTO findIpoById(String ipoId) throws IpoNotFoundException;

    IpoDTO updateIpo(String ipoId, IpoDTO newFields) throws IpoNotFoundException, CompanyNotFoundException, StockExchangeNotFoundException, FieldNotFoundException;

    void deleteIpo(String ipoId) throws IpoNotFoundException;

    List<IpoDTO> getAllIpos();

    List<IpoDTO> getIpoByAttrNames(String stockExchangeName, String companyName);
}
