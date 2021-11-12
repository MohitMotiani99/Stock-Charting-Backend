package com.StockCharting.StockExchange.service;

import com.StockCharting.StockExchange.dto.StockExchangeDTO;
import com.StockCharting.StockExchange.exception.StockExchangeAlreadyExistsException;
import com.StockCharting.StockExchange.exception.StockExchangeNotFoundException;

import java.util.List;

public interface StockExchangeService {
    StockExchangeDTO saveStockExchange(StockExchangeDTO stockExchange) throws StockExchangeAlreadyExistsException;

    StockExchangeDTO findStockExchangeById(String stockExchangeId) throws StockExchangeNotFoundException;

    StockExchangeDTO findStockExchangeByName(String stockExchangeName) throws StockExchangeNotFoundException;

    List<StockExchangeDTO> findAllStockExchanges();

    StockExchangeDTO updateStockExchange(String stockExchangeId, StockExchangeDTO newFields) throws StockExchangeNotFoundException, StockExchangeAlreadyExistsException;
}
