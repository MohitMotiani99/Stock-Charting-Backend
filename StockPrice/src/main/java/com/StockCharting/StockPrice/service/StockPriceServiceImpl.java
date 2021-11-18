package com.StockCharting.StockPrice.service;

import com.StockCharting.StockPrice.dto.*;
import com.StockCharting.StockPrice.entity.StockPrice;
import com.StockCharting.StockPrice.exception.CompanyNotFoundException;
import com.StockCharting.StockPrice.exception.FieldNotFoundException;
import com.StockCharting.StockPrice.exception.SectorNotFoundException;
import com.StockCharting.StockPrice.exception.StockExchangeNotFoundException;
import com.StockCharting.StockPrice.mapper.StockPriceMapper;
import com.StockCharting.StockPrice.mapper.StockSeries1Mapper;
import com.StockCharting.StockPrice.repository.StockPriceRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockPriceServiceImpl implements StockPriceService{

    public final String UPLOAD_DIR=  "C:\\Users\\support\\Downloads\\StockCharting\\StockPrice\\src\\main\\resources\\static\\StockPriceData";



    @Autowired
    private StockPriceRepository stockPriceRepository;

    @Autowired
    private StockPriceMapper stockPriceMapper;
    @Autowired
    private StockSeries1Mapper stockSeries1Mapper;

    @Autowired
    private RestTemplate restTemplate;


    public void uploadFile(MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public List<StockPriceDTO> uploadPriceSheet(MultipartFile multipartFile,String stockExchangeName,String companyName) throws IOException, StockExchangeNotFoundException, CompanyNotFoundException {

        if(stockExchangeName==null || stockExchangeName.length()==0)
            throw new StockExchangeNotFoundException("Empty Stock Exchange Field");
        if(companyName==null || companyName.length()==0)
            throw new CompanyNotFoundException("Empty Company Name Field");

        try{
            restTemplate.getForObject("http://localhost:8087/exchanges/searchByName?stockExchangeName="+stockExchangeName,StockExchangeDTO.class);
        }
        catch (HttpClientErrorException exception){
            throw new StockExchangeNotFoundException("Stock Exchange "+ stockExchangeName + " Not Available");
        }
        CompanyDTO companyDTO = null;
        try {
            companyDTO = restTemplate.getForObject("http://localhost:8088/companies/searchByName?companyName=" + companyName, CompanyDTO.class);
        } catch (HttpClientErrorException e){
            throw new CompanyNotFoundException("Company "+companyName+" Not Found");
        }
        uploadFile(multipartFile);

        FileInputStream file = new FileInputStream(new File(UPLOAD_DIR+File.separator+multipartFile.getOriginalFilename()));
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");

        List<StockPrice> stockPriceList = new ArrayList<>();

        Double lastVal =0.0;

        while(rowIterator.hasNext()){

            Row row = rowIterator.next();
            if(row.getRowNum()==0) continue;
            if(row.getCell(0).getCellType()==Cell.CELL_TYPE_BLANK && row.getCell(1).getCellType()==Cell.CELL_TYPE_BLANK &&
                    row.getCell(2).getCellType()==Cell.CELL_TYPE_BLANK && row.getCell(3).getCellType()==Cell.CELL_TYPE_BLANK &&
                    row.getCell(4).getCellType()==Cell.CELL_TYPE_BLANK)
                break;

            Iterator<Cell> cellIterator = row.cellIterator();
            StockPrice stockPrice = new StockPrice();




            LocalDate localDate = null;

            while(cellIterator.hasNext()){
                Cell cell = cellIterator.next();
                if(cell.getCellType()!=Cell.CELL_TYPE_BLANK) {
                    //System.out.println(cell.getStringCellValue().trim());
                    if(cell.getColumnIndex()==0)
                        localDate = LocalDate.parse(cell.getStringCellValue());
                    else if(cell.getColumnIndex()==5){
                        if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC)
                            stockPrice.setPrice(cell.getNumericCellValue());
                        else
                            stockPrice.setPrice(0.0);
                    }

                }
            }

            assert companyDTO != null;
            stockPrice.setCompanyCode(companyDTO.getStockExchangeCodes().get(stockExchangeName));
            stockPrice.setStockExchangeName(stockExchangeName);

            assert localDate != null;
            stockPrice.setLocalDate(localDate);
            if(!(stockPrice.getCompanyCode()==null && stockPrice.getLocalDate()==null && stockPrice.getPrice()==null && stockPrice.getStockExchangeName()==null)) {
                //System.out.println(stockPrice);
                stockPriceList.add(stockPrice);
            }
        }

        return stockPriceRepository.saveAll(stockPriceList)
                .stream()
                .map(stockPrice -> stockPriceMapper.map(stockPrice,StockPriceDTO.class))
                .collect(Collectors.toList());
    }

    public List<StockPrice> getALlStockPriceData(){
        return stockPriceRepository.findAll();
    }

    public String getMonth(String m){
        switch (m){
            case "Jan":
                return "01";
            case "Feb":
                return "02";
            case "Mar":
                return "03";
            case "Apr":
                return "04";
            case "May":
                return "05";
            case "Jun":
                return "06";
            case "Jul":
                return "07";
            case "Aug":
                return "08";
            case "Sep":
                return "09";
            case "Oct":
                return "10";
            case "Nov":
                return "11";
            case "Dec":
                return "12";
        }
        return null;
    }

    public String getDate(String start){
        return start.substring(11,15)+"-"+getMonth(start.substring(4,7))+"-"+start.substring(8,10);
    }

    @Override
    public ChartResponse getStockPricesOvertimeForACompany(String stockExchangeName, String companyCode,String start,String end) throws StockExchangeNotFoundException, CompanyNotFoundException {
        LocalDate startDate=LocalDate.parse(getDate(start));
        LocalDate endDate=LocalDate.parse(getDate(end));

        try{
            restTemplate.getForObject("http://localhost:8087/exchanges/searchByName?stockExchangeName="+stockExchangeName,StockExchangeDTO.class);
        } catch (HttpClientErrorException e){
            throw new StockExchangeNotFoundException("Stock Exchange "+stockExchangeName+" Not Available");
        }

        try{
            List<CompanyDTO> companyDTOList = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://localhost:8088/companies/", CompanyDTO[].class)));
            long cnt = companyDTOList.stream()
                    .filter(companyDTO -> companyDTO.getStockExchangeCodes().containsKey(stockExchangeName) && companyDTO.getStockExchangeCodes().get(stockExchangeName).equals(companyCode))
                    .count();
            System.out.println(cnt);
            if(cnt==0)
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

        } catch (HttpClientErrorException e){
            throw new CompanyNotFoundException("Company with code "+companyCode+" Not Found");
        }

        List<StockSeries1> stockSeries1List = getALlStockPriceData()
                .stream()
                .filter(stockPrice -> stockPrice.getStockExchangeName().equals(stockExchangeName) && stockPrice.getCompanyCode().equals(companyCode) && stockPrice.getLocalDate().isAfter(startDate) && stockPrice.getLocalDate().isBefore(endDate))
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

//    public ChartResponse getStockPricesOvertimeForSector(String sectorName,String start,String end) {
//        List<CompanyDTO> companyDTOList = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://localhost:8088/companies/", CompanyDTO[].class)));
//        Set<String> companySet = companyDTOList.stream().map(CompanyDTO::getCompanyName).collect(Collectors.toSet());
//
//        LocalDate startDate=LocalDate.parse(getDate(start));
//        LocalDate endDate=LocalDate.parse(getDate(end));
//
//        List<StockSeries1> stockSeries1List = stockPriceRepository.findAll()
//                .stream()
//                .filter(stockPrice -> {
//                    List<CompanyDTO> companyDTOList1 = companyDTOList
//                                                        .stream()
//                                        .filter(companyDTO -> companyDTO.getStockExchangeCodes().containsKey(stockPrice.getStockExchangeName()) && companyDTO.getStockExchangeCodes().get(stockPrice.getStockExchangeName()).equals(stockPrice.getCompanyCode()))
//                                        .collect(Collectors.toList());
//                    assert companyDTOList1.size()==1;
//                    return companyDTOList1.get(0).getSector().equals(sectorName);
//                })
//                .map(stockPrice -> stockSeries1Mapper.map(stockPrice,StockSeries1.class))
//                .collect(Collectors.toList());
//
//        Map<LocalDate,List<StockSeries1>> map = stockSeries1List.stream().collect(Collectors.groupingBy(StockSeries1::getLocalDate));
//        List<StockSeries1> stockSeries1AnsList = new ArrayList<>();
//        for (LocalDate localDate:map.keySet()){
//            StockSeries1 stockSeries1 = new StockSeries1();
//            Double sum = 0.0;
//            for(StockSeries1 stockSeries11 : map.get(localDate))
//                sum+=stockSeries11.getPrice();
//            stockSeries1AnsList.add(new StockSeries1(sum/map.get(localDate).size(),localDate));
//        }
//
//        ChartResponse chartResponse = new ChartResponse();
//
//        chartResponse.setStockSeries1List(stockSeries1AnsList.stream()
//                .sorted((a,b)->{
////                    System.out.println(a);
////                    System.out.println(b);
//                    if(a.getLocalDate().isAfter(b.getLocalDate()))
//                        return 1;
//                    else if(a.getLocalDate().isBefore(b.getLocalDate()))
//                        return -1;
//                    return 0;
//                })
//                .filter(stockSeries1 -> stockSeries1.getLocalDate().isAfter(startDate) && stockSeries1.getLocalDate().isBefore(endDate))
//                .collect(Collectors.toList()));
//
//        double maxPrice=Double.MIN_VALUE,minPrice=Double.MAX_VALUE,growth,avgPrice,sum=0.0;
//        for(StockSeries1 stockSeries1:chartResponse.getStockSeries1List()){
//            maxPrice=Double.max(maxPrice,stockSeries1.getPrice());
//            minPrice=Double.max(minPrice,stockSeries1.getPrice());
//            sum+=stockSeries1.getPrice();
//        }
//        chartResponse.setMaxPrice(maxPrice);
//        chartResponse.setMinPrice(minPrice);
//        chartResponse.setAvgPrice(sum/chartResponse.getStockSeries1List().size());
//        chartResponse.setGrowth(chartResponse.getStockSeries1List().get(chartResponse.getStockSeries1List().size()-1).getPrice()-chartResponse.getStockSeries1List().get(0).getPrice());
//
//        return chartResponse;
//    }

    @Override
    public ChartResponse getStockPricesOvertimeForStockExchange(String stockExchangeName, String start, String end) throws StockExchangeNotFoundException {

        LocalDate startDate=LocalDate.parse(getDate(start));
        LocalDate endDate=LocalDate.parse(getDate(end));

        try{
            restTemplate.getForObject("http://localhost:8087/exchanges/searchByName?stockExchangeName="+stockExchangeName,StockExchangeDTO.class);
        } catch (HttpClientErrorException e){
            throw new StockExchangeNotFoundException("Stock Exchange "+stockExchangeName+" Not Available");
        }

        List<StockSeries1> stockSeries1List = stockPriceRepository.findAll()
                .stream()
                .filter(stockPrice -> stockPrice.getStockExchangeName().equals(stockExchangeName) && stockPrice.getLocalDate().isAfter(startDate) && stockPrice.getLocalDate().isBefore(endDate))
                .map(stockPrice -> stockSeries1Mapper.map(stockPrice,StockSeries1.class))
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


        System.out.println(map);

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

    public ChartResponse getStockPricesOvertimeForASector(String sectorName, String start, String end) throws CompanyNotFoundException, SectorNotFoundException {
        LocalDate startDate=LocalDate.parse(getDate(start));
        LocalDate endDate=LocalDate.parse(getDate(end));

        try{
            restTemplate.getForObject("http://localhost:8084/sectors/searchByName?sectorName="+sectorName, SectorDTO.class);
        } catch (HttpClientErrorException e){
            throw new SectorNotFoundException("Sector "+sectorName+" Not Available");
        }

        List<CompanyDTO> companyDTOList = Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://localhost:8088/companies/", CompanyDTO[].class)));


        List<StockSeries1> stockSeries1List = getALlStockPriceData()
                .stream()
                .filter(stockPrice -> {
                    return stockPrice.getLocalDate().isAfter(startDate) && stockPrice.getLocalDate().isBefore(endDate) &&
                            companyDTOList.stream()
                            .filter(companyDTO -> companyDTO.getSector().equals(sectorName) && companyDTO.getStockExchangeCodes().containsKey(stockPrice.getStockExchangeName()) && companyDTO.getStockExchangeCodes().get(stockPrice.getStockExchangeName()).equals(stockPrice.getCompanyCode()))
                            .count()==1;
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

    @Override
    public StockPriceDTO saveStockPrice(IpoDTO ipoDTO) throws StockExchangeNotFoundException, CompanyNotFoundException, FieldNotFoundException {
        String stockExchangeName = ipoDTO.getStockExchangeName();
        String companyName = ipoDTO.getCompanyName();
        Double price = ipoDTO.getPricePerShare();
        LocalDate localDate = ipoDTO.getOpenDate();

        CompanyDTO companyDTO;
        try{
            companyDTO = restTemplate.getForObject("http://localhost:8088/companies/searchByName?companyName="+companyName,CompanyDTO.class);
            if(companyDTO!=null && !companyDTO.getStockExchangeCodes().containsKey(stockExchangeName))
                throw new StockExchangeNotFoundException("Stock Exchange with Name "+stockExchangeName+" Does Not Exist For this Company");
        } catch (HttpClientErrorException e){
            throw new CompanyNotFoundException("Company Name "+companyName+" Not Found");
        }

        assert companyDTO != null;
        StockPriceDTO stockPriceDTO = StockPriceDTO.builder()
                .stockExchangeName(stockExchangeName)
                .companyCode(companyDTO.getStockExchangeCodes().get(stockExchangeName))
                .price(price)
                .localDate(localDate)
                .build();

        if(stockExchangeName==null || stockExchangeName.length()==0)
            throw new StockExchangeNotFoundException("Empty Stock Exchange Field");
        if(companyName==null || companyName.length()==0)
            throw new CompanyNotFoundException("Empty Company Name Field");
        if(price==null)
            throw new FieldNotFoundException("Empty Price Field");
        if(localDate==null)
            throw new FieldNotFoundException("Empty Date Field");

        return stockPriceMapper.map(stockPriceRepository.save(stockPriceMapper.map(stockPriceDTO,StockPrice.class)),StockPriceDTO.class);
    }

}
