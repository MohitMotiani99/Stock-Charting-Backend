//package com.StockCharting.StockExchange.service;
//
//import com.StockCharting.StockExchange.entity.StockExchange;
//import com.StockCharting.StockExchange.exception.StockExchangeIdNotFoundException;
//import com.StockCharting.StockExchange.repository.StockExchangeRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class StockExchangeServiceTest {
//
//    @MockBean
//    private StockExchangeRepository stockExchangeRepository;
//
//    @Autowired
//    private StockExchangeService stockExchangeService;
//
//    private StockExchange stockExchange;
//
//    @BeforeEach
//    void setUp(){
//        stockExchange = StockExchange.builder()
//                .id(1L)
//                .stockExchangeName("XYZ")
//                .brief("100% Risk free stock")
//                .contactAddress("xyz@exchanges.com")
//                .remarks("Great Stock Over all")
//                .build();
//    }
//
//    @Test
//    @DisplayName("Save New Company")
//    public void saveStockExchange_thenReturnStockExchange(){
//
//        StockExchange newStockExchange = StockExchange.builder()
//                .stockExchangeName("XYZ")
//                .brief("100% Risk free stock")
//                .contactAddress("xyz@exchanges.com")
//                .remarks("Great Stock Over all")
//                .build();
//
//        Mockito.when(stockExchangeRepository.save(newStockExchange))
//                .thenReturn(stockExchange);
//
//        StockExchange found = stockExchangeService.saveStockExchange(newStockExchange);
//        assertSame(found,stockExchange);
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Id with a Valid StockExchange Id")
//    public void findStockExchangeById_whenValidId_thenStockExchangeFoundReturned() throws StockExchangeIdNotFoundException {
//        Long id =1L;
//
//        Mockito.when(stockExchangeRepository.findById(id))
//                .thenReturn(Optional.ofNullable(stockExchange));
//
//        StockExchange found = stockExchangeService.findStockExchangeById(id);
//        assertSame(found,stockExchange);
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Id with a InValid StockExchange Id")
//    public void findStockExchangeById_whenInValidId_thenThrowException() throws StockExchangeIdNotFoundException {
//        Long id =2L;
//
//        Mockito.when(stockExchangeRepository.findById(id))
//                .thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(StockExchangeIdNotFoundException.class,()->{
//            stockExchangeService.findStockExchangeById(id);
//        });
//
//        assertEquals(exception.getMessage(),"Stock Exchange Id Not Available");
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Name with a Valid StockExchange Name")
//    public void findStockExchangeByName_whenValidCompanyName_thenStockExchangeShouldFoundReturned(){
//        String stockExchangeName = "xyz";
//        List<StockExchange> stockExchangeList = new ArrayList<>();
//        stockExchangeList.add(stockExchange);
//
//        Mockito.when(stockExchangeRepository.findByStockExchangeNameIgnoreCase(stockExchangeName))
//                .thenReturn(stockExchangeList);
//
//        List<StockExchange> foundList = stockExchangeService.findStockExchangeByName(stockExchangeName);
//        assertSame(foundList,stockExchangeList);
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Name with a InValid StockExchange Name")
//    public void findStockExchangeByName_whenInValidCompanyName_thenStockExchangeReturnNull(){
//        String stockExchangeName = "abc";
//        List<StockExchange> stockExchangeList = new ArrayList<>();
//
//        Mockito.when(stockExchangeRepository.findByStockExchangeNameIgnoreCase(stockExchangeName))
//                .thenReturn(stockExchangeList);
//
//        List<StockExchange> foundList = stockExchangeService.findStockExchangeByName(stockExchangeName);
//        assertSame(foundList,stockExchangeList);
//    }
//
//    @Test
//    @DisplayName("Find All Stock Exchanges")
//    public void findAllStockExchanges_thenReturnStockExchangesList(){
//        List<StockExchange> stockExchangeList = new ArrayList<>();
//        stockExchangeList.add(stockExchange);
//
//        Mockito.when(stockExchangeRepository.findAll())
//                .thenReturn(stockExchangeList);
//
//        List<StockExchange> foundList = stockExchangeService.findAllStockExchanges();
//        assertSame(foundList,stockExchangeList);
//    }
//
//    @Test
//    @DisplayName("Update Stock Exchange By Id with a Valid StockExchange Id")
//    public void updateStockExchange_whenValidId_thenStockExchangeUpdatedReturned() throws StockExchangeIdNotFoundException {
//        Long id = 1L;
//
//        StockExchange newStockExchange = StockExchange.builder()
//                .id(1L)
//                .stockExchangeName("XYZ Stock Exchange")
//                .brief("100% Risk free stock")
//                .contactAddress("xyz@exchanges.com")
//                .remarks("Great Stock Over all")
//                .build();
//
//        StockExchange newFields = StockExchange.builder()
//                .stockExchangeName("XYZ Stock Exchange")
//                .build();
//
//        Mockito.when(stockExchangeRepository.findById(id))
//                .thenReturn(Optional.ofNullable(stockExchange));
//        Mockito.when(stockExchangeRepository.save(newStockExchange))
//                .thenReturn(newStockExchange);
//
//        StockExchange found = stockExchangeService.updateStockExchange(id,newFields);
//        assertSame(found,newStockExchange);
//
//
//
//    }
//
//    @Test
//    @DisplayName("Update Stock Exchange By Id with a InValid StockExchange Id")
//    public void updateStockExchange_whenInValidId_thenThrowException() throws StockExchangeIdNotFoundException {
//        Long id = 2L;
//
//        StockExchange newFields = StockExchange.builder()
//                .stockExchangeName("XYZ Stock Exchange")
//                .build();
//
//        Mockito.when(stockExchangeRepository.findById(id))
//                .thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(StockExchangeIdNotFoundException.class,()->{
//            stockExchangeService.updateStockExchange(id,newFields);
//        });
//
//        assertEquals(exception.getMessage(),"Stock Exchange Id Not Available");
//    }
//
//}