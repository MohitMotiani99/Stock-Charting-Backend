package com.StockCharting.Sector.service;

import com.StockCharting.Sector.dto.CompanyDTO;
import com.StockCharting.Sector.dto.SectorDTO;
import com.StockCharting.Sector.entity.Sector;
import com.StockCharting.Sector.exception.SectorAlreadyExistsException;
import com.StockCharting.Sector.exception.SectorNotFoundException;
import com.StockCharting.Sector.mapper.SectorMapper;
import com.StockCharting.Sector.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SectorServiceImpl implements SectorService{
    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private SectorMapper sectorMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public SectorDTO saveSector(SectorDTO sectorDTO) throws SectorAlreadyExistsException, SectorNotFoundException {
        if(sectorDTO.getSectorName()!=null){
            Optional<Sector> sectorOptional = sectorRepository.findBySectorNameIgnoreCase(sectorDTO.getSectorName());
            if(sectorOptional.isPresent())
                throw new SectorAlreadyExistsException("Sector with name "+sectorDTO.getSectorName()+" Already Exists");
        }
        else
            throw new SectorAlreadyExistsException("Sector Name Cannot Be Empty");

        if(sectorDTO.getSectorName()!=null && sectorDTO.getSectorName().isEmpty())
            throw new SectorNotFoundException("Sector Name Cannot Be Empty");

        return sectorMapper.map(sectorRepository.save(sectorMapper.map(sectorDTO, Sector.class)),SectorDTO.class);
    }

    @Override
    public SectorDTO findSectorByName(String sectorName) throws SectorNotFoundException {
        Optional<Sector> sectorOptional = sectorRepository.findBySectorNameIgnoreCase(sectorName);
        if(sectorOptional.isEmpty())
            throw new SectorNotFoundException("Sector " + sectorName +" Not Available");
        return sectorMapper.map(sectorOptional.get(),SectorDTO.class);
    }

    @Override
    public SectorDTO findSectorById(String sectorId) throws SectorNotFoundException {
        Optional<Sector> sectorOptional = sectorRepository.findBySectorId(sectorId);
        if(sectorOptional.isEmpty())
            throw new SectorNotFoundException("Sector " + sectorId +" Not Available");
        return sectorMapper.map(sectorOptional.get(),SectorDTO.class);
    }

    @Override
    public SectorDTO updateSector(String sectorId, SectorDTO newFields) throws SectorNotFoundException, SectorAlreadyExistsException {
        Optional<Sector> sectorOptional = sectorRepository.findBySectorId(sectorId);
        if(sectorOptional.isEmpty())
            throw new SectorNotFoundException("Sector " + sectorId +" Not Available");

        if(newFields.getSectorName()!=null){
            Optional<Sector> sectorOptional1 = sectorRepository.findBySectorNameIgnoreCase(newFields.getSectorName());
            if(sectorOptional1.isPresent() && !sectorOptional1.get().getSectorId().equals(sectorId))
                throw new SectorAlreadyExistsException("Sector with name "+newFields.getSectorName()+" Already Exists");

            //List<CompanyDTO> companyDTOList = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://COMPANY-SERVICE/companies/", CompanyDTO[].class)));
//            companyDTOList.stream()
//                    .filter(companyDTO -> companyDTO.getSector().equals(sectorOptional.get().getSectorName()))
//                    .forEach(companyDTO -> restTemplate.put("http://COMPANY-SERVICE/companies/update?companyId="+companyDTO.getCompanyId()));
        }

        if(newFields.getSectorName()!=null && newFields.getSectorName().isEmpty())
            throw new SectorNotFoundException("Sector Name Cannot Be Empty");

        Sector sector = sectorOptional.get();

        sector.setSectorName(newFields.getSectorName()==null?sector.getSectorName(): newFields.getSectorName());
        sector.setBrief(newFields.getBrief()==null?sector.getBrief(): newFields.getBrief());

        return sectorMapper.map(sector,SectorDTO.class);

    }

    @Override
    public void deleteSector(String sectorId) throws SectorNotFoundException {
        Optional<Sector> sectorOptional = sectorRepository.findBySectorId(sectorId);
        if(sectorOptional.isEmpty())
            throw new SectorNotFoundException("Sector " + sectorId +" Not Available");
        sectorRepository.deleteBySectorId(sectorId);
    }

    @Override
    public List<SectorDTO> getAllSectors() {
        return sectorRepository.findAll()
                .stream()
                .map(sector -> sectorMapper.map(sector,SectorDTO.class))
                .collect(Collectors.toList());
    }

}
