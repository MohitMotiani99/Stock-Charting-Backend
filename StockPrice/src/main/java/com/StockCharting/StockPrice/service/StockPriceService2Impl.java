package com.StockCharting.StockPrice.service;

import com.StockCharting.StockPrice.dto.*;
import com.StockCharting.StockPrice.exception.CompanyNotFoundException;
import com.StockCharting.StockPrice.exception.SectorNotFoundException;
import com.StockCharting.StockPrice.exception.StockExchangeNotFoundException;
import com.StockCharting.StockPrice.mapper.StockPriceMapper;
import com.StockCharting.StockPrice.mapper.StockSeries1Mapper;
import com.StockCharting.StockPrice.repository.StockPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockPriceService2Impl implements StockPriceService2{
    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Autowired
    private StockPriceMapper stockPriceMapper;
    @Autowired
    private StockSeries1Mapper stockSeries1Mapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private StockPriceServiceImpl sps;

    public ChartResponse getStockPricesOvertimeForASector(String sectorName, String companyName, String start, String end) throws CompanyNotFoundException, SectorNotFoundException {
        LocalDate startDate=LocalDate.parse(sps.getDate(start));
        LocalDate endDate=LocalDate.parse(sps.getDate(end));

        try{
            restTemplate.getForObject("http://localhost:8084/sectors/searchByName?sectorName="+sectorName, SectorDTO.class);
        } catch (HttpClientErrorException e){
            throw new SectorNotFoundException("Sector "+sectorName+" Not Available");
        }

        CompanyDTO companyDTO;
        try{
            companyDTO = restTemplate.getForObject("http://localhost:8088/companies/searchByName?companyName="+companyName, CompanyDTO.class);
        } catch (HttpClientErrorException e){
            throw new CompanyNotFoundException("Company with Name "+companyName+" Not Found");
        }

        List<StockSeries1> stockSeries1List = sps.getALlStockPriceData()
                .stream()
                .filter(stockPrice -> {
                    assert companyDTO != null;
                    return companyDTO.getStockExchangeCodes().containsKey(stockPrice.getStockExchangeName()) && stockPrice.getLocalDate().isAfter(startDate) && stockPrice.getLocalDate().isBefore(endDate);
                })
                .map(stockPrice -> stockSeries1Mapper.map(stockPrice, StockSeries1.class))
                .collect(Collectors.toList());


        System.out.println(stockSeries1List);

        Double cprice=0.0;
        for (StockSeries1 series1 : stockSeries1List) {
            cprice += (series1.getPrice());
        }

        if(stockSeries1List.size()!=0)
            cprice = cprice/stockSeries1List.size();
        else
            cprice =0.0;



        Map<LocalDate,List<StockSeries1>> map = stockSeries1List.stream().collect(Collectors.groupingBy(StockSeries1::getLocalDate));

        Double runningSum=0.0;
        int num=0;
        for(LocalDate ld=startDate;ld.isBefore(endDate);ld=ld.plusDays(1)){
            if(!map.containsKey(ld)){
                List<StockSeries1> list = new ArrayList<>();
                double val = (num==0)?runningSum:runningSum/num;
                list.add(new StockSeries1(val,ld));
                map.put(ld,list);
                runningSum+=val;
                num++;
            }
            else if(map.containsKey(ld) && map.get(ld).size()==1 && map.get(ld).get(0).getPrice()==0.0)
            {
                List<StockSeries1> list = new ArrayList<>();
                double val = (num==0)?runningSum:runningSum/num;
                list.add(new StockSeries1(val,ld));
                map.put(ld,list);
                runningSum+=val;
                num++;
            }
            else{
                for (StockSeries1 stockSeries1:map.get(ld)) {
                    runningSum += stockSeries1.getPrice();
                    num++;
                }
            }
        }


        System.out.println(map);

        List<StockSeries1> stockSeries1AnsList = new ArrayList<>();
        for (LocalDate localDate:map.keySet()){
            StockSeries1 stockSeries1 = new StockSeries1();
            Double sum = 0.0;
            for(StockSeries1 stockSeries11 : map.get(localDate))
                sum+=stockSeries11.getPrice();
            stockSeries1AnsList.add(new StockSeries1(sum/map.get(localDate).size(),localDate));
        }
        //
        List<StockSeries1> finList = stockSeries1AnsList.stream()
                .sorted((a,b)->{
//                    System.out.println(a);
//                    System.out.println(b);
                    if(a.getLocalDate().isAfter(b.getLocalDate()))
                        return 1;
                    else if(a.getLocalDate().isBefore(b.getLocalDate()))
                        return -1;
                    return 0;
                })
                .collect(Collectors.toList());
        //
        System.out.println(finList.size());
        System.out.println(ChronoUnit.DAYS.between(startDate,endDate));
        //
        ChartResponse chartResponse = new ChartResponse();

        chartResponse.setStockSeries1List(finList);

        double maxPrice=Double.MIN_VALUE,minPrice=Double.MAX_VALUE,growth,avgPrice,sum=0.0;
        for(StockSeries1 stockSeries1:chartResponse.getStockSeries1List()){
            maxPrice=Double.max(maxPrice,stockSeries1.getPrice());
            minPrice=Double.max(minPrice,stockSeries1.getPrice());
            sum+=stockSeries1.getPrice();
        }
        chartResponse.setMaxPrice(maxPrice);
        chartResponse.setMinPrice(minPrice);
        if(chartResponse.getStockSeries1List().size()!=0) {
            chartResponse.setAvgPrice(sum / chartResponse.getStockSeries1List().size());
            chartResponse.setGrowth(chartResponse.getStockSeries1List().get(chartResponse.getStockSeries1List().size() - 1).getPrice() - chartResponse.getStockSeries1List().get(0).getPrice());
        }
        else{
            chartResponse.setAvgPrice(null);
            chartResponse.setGrowth(null);
        }



        return chartResponse;
    }
}
