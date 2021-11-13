package com.StockCharting.IPO.service;

import com.StockCharting.IPO.dto.CompanyDTO;
import com.StockCharting.IPO.dto.IpoDTO;
import com.StockCharting.IPO.dto.StockExchangeDTO;
import com.StockCharting.IPO.entity.Ipo;
import com.StockCharting.IPO.exception.CompanyNotFoundException;
import com.StockCharting.IPO.exception.FieldNotFoundException;
import com.StockCharting.IPO.exception.IpoNotFoundException;
import com.StockCharting.IPO.exception.StockExchangeNotFoundException;
import com.StockCharting.IPO.mapper.IpoMapper;
import com.StockCharting.IPO.repository.IpoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IpoServiceImpl implements IpoService{

    @Autowired
    private IpoRepository ipoRepository;

    @Autowired
    private IpoMapper ipoMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public IpoDTO saveIpo(IpoDTO ipoDTO) throws CompanyNotFoundException, StockExchangeNotFoundException, FieldNotFoundException {
        CompanyDTO companyDTO;
        if(ipoDTO.getCompanyName()!=null && !ipoDTO.getCompanyName().isEmpty()){
            try {
                companyDTO = restTemplate.getForObject("http://localhost:8088/companies/searchByName?companyName="+ipoDTO.getCompanyName(),CompanyDTO.class);
            }
            catch (HttpClientErrorException exception){
                throw new CompanyNotFoundException("Company "+ipoDTO.getCompanyName()+" Not Available");
            }
        }
        else
            throw new CompanyNotFoundException("Company Name Cannot Be Empty");
        if(ipoDTO.getStockExchangeName()!=null && !ipoDTO.getStockExchangeName().isEmpty()){
            try {
                StockExchangeDTO stockExchangeDTO = restTemplate.getForObject("http://localhost:8087/exchanges/searchByName?stockExchangeName="+ipoDTO.getStockExchangeName(),StockExchangeDTO.class);
            }
            catch (HttpClientErrorException exception){
                throw new StockExchangeNotFoundException("Stock Exchange "+ipoDTO.getStockExchangeName()+" Not Available");
            }
        }
        else
            throw new StockExchangeNotFoundException("Stock Exchange Name Cannot Be Empty");

        if(ipoDTO.getPricePerShare()==null)
            throw new FieldNotFoundException("Stock Price Per Share Cannot Be Empty");
        if(ipoDTO.getTotalStocks()==null || ipoDTO.getTotalStocks()==0)
            throw new FieldNotFoundException("Total Stocks Cannot Be Empty");
        if(ipoDTO.getOpenDate()==null)
            throw new FieldNotFoundException("Open Date Cannot Be Empty");

        return ipoMapper.map(ipoRepository.save(ipoMapper.map(ipoDTO, Ipo.class)),IpoDTO.class);
    }

    @Override
    public IpoDTO findIpoById(String ipoId) throws IpoNotFoundException {
        Optional<Ipo> ipoOptional = ipoRepository.findByIpoId(ipoId);
        if(ipoOptional.isEmpty())
            throw new IpoNotFoundException("IPO "+ipoId+" Not Available");

        return ipoMapper.map(ipoOptional.get(),IpoDTO.class);

    }

    @Override
    public IpoDTO updateIpo(String ipoId, IpoDTO newFields) throws IpoNotFoundException, CompanyNotFoundException, StockExchangeNotFoundException, FieldNotFoundException {

        Optional<Ipo> ipoOptional = ipoRepository.findByIpoId(ipoId);
        if(ipoOptional.isEmpty())
            throw new IpoNotFoundException("IPO "+ipoId+" Not Available");

        if(newFields.getCompanyName()!=null && !newFields.getCompanyName().isEmpty()){
            try {
                CompanyDTO companyDTO = restTemplate.getForObject("http://localhost:8088/companies/searchByName?companyName="+newFields.getCompanyName(),CompanyDTO.class);
            }
            catch (HttpClientErrorException exception){
                throw new CompanyNotFoundException("Company "+newFields.getCompanyName()+" Not Available");
            }
        }
        if(newFields.getStockExchangeName()!=null && !newFields.getStockExchangeName().isEmpty()){
            try {
                StockExchangeDTO stockExchangeDTO = restTemplate.getForObject("http://localhost:8087/exchanges/searchByName?stockExchangeName="+newFields.getStockExchangeName(),StockExchangeDTO.class);
            }
            catch (HttpClientErrorException exception){
                throw new StockExchangeNotFoundException("Stock Exchange "+newFields.getStockExchangeName()+" Not Available");
            }
        }

        if(newFields.getTotalStocks()!=null && newFields.getTotalStocks()==0)
            throw new FieldNotFoundException("Total Stocks Cannot Be Empty");

        Ipo ipo = ipoOptional.get();

        ipo.setCompanyName(newFields.getCompanyName()==null?ipo.getCompanyName():newFields.getCompanyName());
        ipo.setOpenDate(newFields.getOpenDate()==null?ipo.getOpenDate():newFields.getOpenDate());
        ipo.setPricePerShare(newFields.getPricePerShare()==null?ipo.getPricePerShare():newFields.getPricePerShare());
        ipo.setStockExchangeName(newFields.getStockExchangeName()==null?ipo.getStockExchangeName():newFields.getStockExchangeName());
        ipo.setRemarks(newFields.getRemarks()==null?ipo.getRemarks():newFields.getRemarks());
        ipo.setTotalStocks(newFields.getTotalStocks()==null?ipo.getTotalStocks():newFields.getTotalStocks());

        return ipoMapper.map(ipoRepository.save(ipo),IpoDTO.class);

    }

    @Override
    public void deleteIpo(String ipoId) throws IpoNotFoundException {
        Optional<Ipo> ipoOptional = ipoRepository.findByIpoId(ipoId);
        if(ipoOptional.isEmpty())
            throw new IpoNotFoundException("IPO "+ipoId+" Not Available");
        ipoRepository.deleteByIpoId(ipoId);
    }

    @Override
    public List<IpoDTO> getAllIpos() {
        return ipoRepository.findAll()
                .stream()
                .map(ipo -> ipoMapper.map(ipo,IpoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<IpoDTO> getIpoByAttrNames(String stockExchangeName, String companyName) {
        return ipoRepository.findByStockExchangeNameAndCompanyName(stockExchangeName,companyName)
                .stream()
                .map(ipo -> ipoMapper.map(ipo,IpoDTO.class))
                .collect(Collectors.toList());
    }
}
