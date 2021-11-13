package com.StockCharting.StockExchange.service;

import com.StockCharting.StockExchange.dto.StockExchangeDTO;
import com.StockCharting.StockExchange.entity.Address;
import com.StockCharting.StockExchange.entity.StockExchange;
import com.StockCharting.StockExchange.exception.StockExchangeAlreadyExistsException;
import com.StockCharting.StockExchange.exception.StockExchangeNotFoundException;
import com.StockCharting.StockExchange.mapper.StockExchangeMapper;
import com.StockCharting.StockExchange.repository.StockExchangeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StockExchangeServiceImpl implements StockExchangeService{

    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Autowired
    private StockExchangeMapper stockExchangeMapper;

    @Override
    public StockExchangeDTO saveStockExchange(StockExchangeDTO stockExchangeDTO) throws StockExchangeAlreadyExistsException {

        if(stockExchangeDTO.getStockExchangeName()!=null){
            Optional<StockExchange> stockExchangeOptional = stockExchangeRepository.findByStockExchangeNameIgnoreCase(stockExchangeDTO.getStockExchangeName());
            if(stockExchangeOptional.isPresent())
                throw new StockExchangeAlreadyExistsException("Stock Exchange with name "+stockExchangeDTO.getStockExchangeName()+" Already Exits");
        }
        else
            throw new StockExchangeAlreadyExistsException("Stock Exchange Name Cannot Be Empty");

        StockExchange stockExchange = stockExchangeMapper.map(stockExchangeDTO,StockExchange.class);
        StockExchange savedStockExchange = stockExchangeRepository.save(stockExchange);
        return stockExchangeMapper.map(savedStockExchange,StockExchangeDTO.class);

    }

    @Override
    public StockExchangeDTO findStockExchangeById(String stockExchangeId) throws StockExchangeNotFoundException {
        Optional<StockExchange> stockExchange = stockExchangeRepository.findByStockExchangeId(stockExchangeId);
        if(stockExchange.isEmpty())
            throw new StockExchangeNotFoundException("Stock Exchange Id Not Available");
        return stockExchangeMapper.map(stockExchange.get(),StockExchangeDTO.class);
    }

    @Override
    public StockExchangeDTO findStockExchangeByName(String stockExchangeName) throws StockExchangeNotFoundException {
        Optional<StockExchange> stockExchange = stockExchangeRepository.findByStockExchangeNameIgnoreCase(stockExchangeName);
        if(stockExchange.isEmpty())
            throw new StockExchangeNotFoundException("Stock Exchange Not Registered");
        return stockExchangeMapper.map(stockExchange.get(),StockExchangeDTO.class);

    }

    @Override
    public List<StockExchangeDTO> findAllStockExchanges() {
        return stockExchangeRepository.findAll()
                .stream()
                .map(stockExchange -> stockExchangeMapper.map(stockExchange,StockExchangeDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public StockExchangeDTO updateStockExchange(String stockExchangeId, StockExchangeDTO newFields) throws StockExchangeNotFoundException, StockExchangeAlreadyExistsException {
        Optional<StockExchange> stockExchange = stockExchangeRepository.findByStockExchangeId(stockExchangeId);
        if(stockExchange.isEmpty())
            throw new StockExchangeNotFoundException("Stock Exchange Id Not Available");
        StockExchange found = stockExchange.get();

        if(newFields.getStockExchangeName()!=null){
            Optional<StockExchange> stockExchangeOptional = stockExchangeRepository.findByStockExchangeNameIgnoreCase(newFields.getStockExchangeName());
            if(stockExchangeOptional.isPresent() && !stockExchangeOptional.get().getStockExchangeId().equals(stockExchangeId))
                throw new StockExchangeAlreadyExistsException("Stock Exchange with name "+newFields.getStockExchangeName()+" Already Exits");
        }

        if(newFields.getStockExchangeName()!=null && newFields.getStockExchangeName().isEmpty())
            throw new StockExchangeNotFoundException("Stock Exchange Name Cannot Be Empty");

        found.setStockExchangeName(newFields.getStockExchangeName()==null? found.getStockExchangeName() : newFields.getStockExchangeName());
        found.setBrief(newFields.getBrief()==null? found.getBrief() : newFields.getBrief());
        found.setRemarks(newFields.getRemarks()==null? found.getRemarks() : newFields.getRemarks());

        Address newAddress = newFields.getContactAddress();
        if(!Objects.isNull(newAddress)) {
            if (found.getContactAddress() == null)
                found.setContactAddress(newAddress);
            else {
                found.getContactAddress().setBuildingNumber(newAddress.getBuildingNumber()==null?found.getContactAddress().getBuildingNumber():newAddress.getBuildingNumber());
                found.getContactAddress().setStreet(newAddress.getStreet()==null?found.getContactAddress().getStreet():newAddress.getStreet());
                found.getContactAddress().setCity(newAddress.getCity()==null?found.getContactAddress().getCity():newAddress.getCity());
                found.getContactAddress().setState(newAddress.getState()==null?found.getContactAddress().getState():newAddress.getState());
                found.getContactAddress().setCountry(newAddress.getCountry()==null?found.getContactAddress().getCountry():newAddress.getCountry());
                found.getContactAddress().setZip(newAddress.getZip()==null?found.getContactAddress().getZip():newAddress.getZip());
            }
        }

        return stockExchangeMapper.map(stockExchangeRepository.save(found),StockExchangeDTO.class);
    }
}
