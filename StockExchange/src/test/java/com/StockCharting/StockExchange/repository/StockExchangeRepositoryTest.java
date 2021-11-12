//package com.StockCharting.StockExchange.repository;
//
//import com.StockCharting.StockExchange.entity.StockExchange;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class StockExchangeRepositoryTest {
//
//    @Autowired
//    private StockExchangeRepository stockExchangeRepository;
//
//    @Autowired
//    private TestEntityManager testEntityManager;
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
//
//        testEntityManager.persist(stockExchange);
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
//        StockExchange found = stockExchangeRepository.save(newStockExchange);
//
//        assertSame(found,stockExchange);
//
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Id with a Valid StockExchange Id")
//    public void findStockExchangeById_whenValidId_thenStockExchangeFoundReturned(){
//        Long id =1L;
//        StockExchange found = stockExchangeRepository.findById(id).get();
//        assertSame(found,stockExchange);
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Id with a InValid StockExchange Id")
//    public void findStockExchangeById_whenInValidId_thenThrowException(){
//        Long id =2L;
//        Optional<StockExchange> found = stockExchangeRepository.findById(id);
//        assertNull(found);
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Name with a Valid StockExchange Name")
//    public void findStockExchangeByName_whenValidCompanyName_thenStockExchangeShouldFoundReturned(){
//        String stockExchangeName = "xyz";
//        List<StockExchange> stockExchangeList = new ArrayList<>();
//        stockExchangeList.add(stockExchange);
//
//        List<StockExchange> foundList = stockExchangeRepository.findByStockExchangeNameIgnoreCase(stockExchangeName);
//        assertSame(foundList,stockExchangeList);
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Name with a InValid StockExchange Name")
//    public void findStockExchangeByName_whenInValidCompanyName_thenStockExchangeReturnNull(){
//        String stockExchangeName = "abc";
//        List<StockExchange> stockExchangeList = new ArrayList<>();
//
//        List<StockExchange> foundList = stockExchangeRepository.findByStockExchangeNameIgnoreCase(stockExchangeName);
//        assertSame(foundList,stockExchangeList);
//    }
//
//    @Test
//    @DisplayName("Find All Stock Exchanges")
//    public void findAllStockExchanges_thenReturnStockExchangesList(){
//        List<StockExchange> stockExchangeList = new ArrayList<>();
//        stockExchangeList.add(stockExchange);
//
//        List<StockExchange> foundList = stockExchangeRepository.findAll();
//        assertSame(foundList,stockExchangeList);
//    }
//
//}