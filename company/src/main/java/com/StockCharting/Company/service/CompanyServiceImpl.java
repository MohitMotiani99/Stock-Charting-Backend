package com.StockCharting.Company.service;

import com.StockCharting.Company.dto.CompanyDTO;
import com.StockCharting.Company.dto.SectorDTO;
import com.StockCharting.Company.dto.StockExchangeDTO;
import com.StockCharting.Company.entity.Company;
import com.StockCharting.Company.exception.CompanyAlreadyExistsException;
import com.StockCharting.Company.exception.CompanyNotFoundException;
import com.StockCharting.Company.exception.SectorNotFoundException;
import com.StockCharting.Company.exception.StockExchangeNotFoundException;
import com.StockCharting.Company.mapper.CompanyMapper;
import com.StockCharting.Company.repository.CompanyRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CompanyDTO saveCompany(CompanyDTO companyDTO) throws StockExchangeNotFoundException, SectorNotFoundException, CompanyAlreadyExistsException, CompanyNotFoundException {
        log.info("Inside saveCompany of CompanyServiceImpl");

        if(companyDTO.getCompanyName()!=null && !companyDTO.getCompanyName().isEmpty()){
            Optional<Company> companyOptional = companyRepository.findByCompanyNameIgnoreCase(companyDTO.getCompanyName());
            if(companyOptional.isPresent())
                throw new CompanyAlreadyExistsException("Company with name "+companyDTO.getCompanyName()+" Already Exists");
        }
        else
            throw new CompanyNotFoundException("Company Name Cannot Be Empty");

        if (companyDTO.getSector()!=null && !companyDTO.getSector().isEmpty()){
            try{
                SectorDTO sectorDTO = restTemplate.getForObject("http://SECTOR-SERVICE/sectors/searchByName?sectorName="+companyDTO.getSector(),SectorDTO.class);
            }
            catch (HttpClientErrorException exception){
                throw new SectorNotFoundException("Sector "+companyDTO.getSector()+" Not Available");
            }
        }

        if(companyDTO.getStockExchangeCodes()!=null && !companyDTO.getStockExchangeCodes().isEmpty()) {
            for (String stockName : companyDTO.getStockExchangeCodes().keySet()) {
                try {
                    StockExchangeDTO stockExchangeDTO = restTemplate.getForObject("http://STOCK-EXCHANGE-SERVICE/exchanges/searchByName?stockExchangeName=" + stockName, StockExchangeDTO.class);
                } catch (HttpClientErrorException exception) {
                    throw new StockExchangeNotFoundException("Stock Exchange "+stockName+" Not Available");
                }
            }
        }
        return companyMapper.map(companyRepository.save(companyMapper.map(companyDTO,Company.class)),CompanyDTO.class);
    }

    @Override
    public CompanyDTO findCompanyById(String companyId) throws CompanyNotFoundException {
        log.info("Inside findCompanyById of CompanyServiceImpl");
        Optional<Company> company= companyRepository.findByCompanyId(companyId);
        if(!company.isPresent())
            throw new CompanyNotFoundException("Company "+ companyId +" Not Available");
        return companyMapper.map(company.get(),CompanyDTO.class);
    }

    @Override
    public CompanyDTO findCompanyByName(String companyName) throws CompanyNotFoundException {
        log.info("Inside findCompanyByName of CompanyServiceImpl");
        Optional<Company> company = companyRepository.findByCompanyNameIgnoreCase(companyName);
        if(!company.isPresent())
            throw new CompanyNotFoundException("Company "+companyName+" Not Available");
        return companyMapper.map(company.get(),CompanyDTO.class);
    }

    @Override
    public CompanyDTO updateCompany(String companyId, CompanyDTO newFields) throws CompanyNotFoundException, StockExchangeNotFoundException, SectorNotFoundException, CompanyAlreadyExistsException {
        log.info("Inside updateCompany of CompanyServiceImpl");
        Optional<Company> found= companyRepository.findByCompanyId(companyId);
        if(!found.isPresent())
            throw new CompanyNotFoundException("Company "+ companyId +" Not Available");

        if(newFields.getCompanyName()!=null){
            Optional<Company> companyOptional = companyRepository.findByCompanyNameIgnoreCase(newFields.getCompanyName());
            if(companyOptional.isPresent() && !companyOptional.get().getCompanyId().equals(companyId))
                throw new CompanyAlreadyExistsException("Company with name "+newFields.getCompanyName()+" Already Exists");
        }

        if(newFields.getCompanyName() != null && newFields.getCompanyName().isEmpty())
            throw new CompanyNotFoundException("Company Name Cannot Be Empty");

        if (newFields.getSector()!=null && newFields.getSector()!=null){
            try{
                SectorDTO sectorDTO = restTemplate.getForObject("http://SECTOR-SERVICE/sectors/searchByName?sectorName="+newFields.getSector(),SectorDTO.class);
            }
            catch (HttpClientErrorException exception){
                throw new SectorNotFoundException("Sector "+newFields.getSector()+" Not Available");
            }
        }

        if(newFields.getStockExchangeCodes()!=null && !newFields.getStockExchangeCodes().isEmpty()) {
            for (String stockName : newFields.getStockExchangeCodes().keySet()) {
                try {
                    StockExchangeDTO stockExchangeDTO = restTemplate.getForObject("http://STOCK-EXCHANGE-SERVICE/exchanges/searchByName?stockExchangeName=" + stockName, StockExchangeDTO.class);
                } catch (HttpClientErrorException exception) {
                    throw new StockExchangeNotFoundException("Stock Exchange" + stockName+" Not Available");
                }
            }
        }


        Company companyFound = found.get();
        companyFound.setCompanyName(newFields.getCompanyName()==null?companyFound.getCompanyName():newFields.getCompanyName());
        companyFound.setBoardOfDirectors(newFields.getBoardOfDirectors()==null?companyFound.getBoardOfDirectors():newFields.getBoardOfDirectors());
        companyFound.setBrief(newFields.getBrief()==null?companyFound.getBrief():newFields.getBrief());
        companyFound.setCeo(newFields.getCeo()==null?companyFound.getCeo():newFields.getCeo());
        companyFound.setListedInStockExchange(newFields.getListedInStockExchange()==null?companyFound.getListedInStockExchange():newFields.getListedInStockExchange());
        companyFound.setTurnover(newFields.getTurnover()==null?companyFound.getTurnover():newFields.getTurnover());
        companyFound.setSector(newFields.getSector()==null?companyFound.getSector():newFields.getSector());
        companyFound.setStockExchangeCodes(newFields.getStockExchangeCodes()==null?companyFound.getStockExchangeCodes():newFields.getStockExchangeCodes());

        return companyMapper.map(companyRepository.save(companyFound),CompanyDTO.class);
    }

    @Override
    public List<CompanyDTO> findAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(company -> companyMapper.map(company,CompanyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCompany(String companyId) throws CompanyNotFoundException {
        log.info("Inside deleteCompany of CompanyServiceImpl");
        Optional<Company> found= companyRepository.findByCompanyId(companyId);
        if(!found.isPresent())
            throw new CompanyNotFoundException("Company "+companyId+" Not Available");

        Gson gson = new Gson();

        companyRepository.deleteById(found.get().getId());
    }


}
