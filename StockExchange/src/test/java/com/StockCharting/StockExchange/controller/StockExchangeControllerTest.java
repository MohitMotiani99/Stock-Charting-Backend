//package com.StockCharting.StockExchange.controller;
//
//import com.StockCharting.StockExchange.entity.Address;
//import com.StockCharting.StockExchange.entity.StockExchange;
//import com.StockCharting.StockExchange.exception.StockExchangeIdNotFoundException;
//import com.StockCharting.StockExchange.service.StockExchangeService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(StockExchangeController.class)
//class StockExchangeControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private StockExchangeService stockExchangeService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private StockExchange stockExchange;
//    private Address contactAddress;
//
//    @BeforeEach
//    void setUp(){
//        contactAddress = Address.builder()
//                .buildingNumber("66G")
//                .street("Peter Street")
//                .city("Austin")
//                .state("Texas")
//                .country("US")
//                .zip("110010")
//                .build();
//
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
//    @DisplayName("Save New Stock Exchange")
//    public void saveStockExchange_thenReturnStockExchange() throws Exception {
//
//         StockExchange newStockExchange = StockExchange.builder()
//                .stockExchangeName("XYZ")
//                .brief("100% Risk free stock")
//                .contactAddress("xyz@exchanges.com")
//                .remarks("Great Stock Over all")
//                .build();
//
//        Mockito.when(stockExchangeService.saveStockExchange(newStockExchange))
//                .thenReturn(newStockExchange);
//
//
//
//        mockMvc.perform(post("/exchanges/save")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        "    \"stockExchangeName\":\"XYZ\",\n" +
//                        "    \"brief\":\"100% Risk free stock\",\n" +
//                        "    \"remarks\":\"Great Stock Over all\",\n" +
//                        "    \"contactAddress\":\"xyz@exchanges.com\"\n" +
//                        "}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.stockExchangeName").value("XYZ"))
//                .andExpect(jsonPath("$.brief").value("100% Risk free stock"))
//                .andExpect(jsonPath("$.remarks").value("Great Stock Over all"))
//                .andExpect(jsonPath("$.contactAddress").value("xyz@exchanges.com"));
//
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Id with a Valid StockExchange Id")
//    public void findStockExchangeById_whenValidId_thenStockExchangeFoundReturned() throws Exception {
//        Mockito.when(stockExchangeService.findStockExchangeById(1L))
//                .thenReturn(stockExchange);
//
//        mockMvc.perform(get("/exchanges/search?id=1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(stockExchange.getId()));
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Id with a InValid StockExchange Id")
//    public void findStockExchangeById_whenInValidId_thenThrowException() throws Exception {
//        Mockito.when(stockExchangeService.findStockExchangeById(2L))
//                .thenThrow(new StockExchangeIdNotFoundException("Stock Exchange Id Not Available"));
//
//        mockMvc.perform(get("/exchanges/search?id=2")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
//                .andExpect(jsonPath("$.message").value("Stock Exchange Id Not Available"));
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Name with a Valid StockExchange Name")
//    public void findStockExchangeByName_whenValidCompanyName_thenStockExchangeShouldFoundReturned() throws Exception{
//        List<StockExchange> stockExchangeList = new ArrayList<>();
//        stockExchangeList.add(stockExchange);
//
//        Mockito.when(stockExchangeService.findStockExchangeByName("xyz"))
//                .thenReturn(stockExchangeList);
//
//        mockMvc.perform(get("/exchanges/searchByName?stockExchangeName=xyz")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].id").value(1L));
//    }
//
//    @Test
//    @DisplayName("Find Stock Exchange By Name with a InValid StockExchange Name")
//    public void findStockExchangeByName_whenInValidCompanyName_thenStockExchangeReturnNull() throws Exception{
//        List<StockExchange> stockExchangeList = new ArrayList<>();
//
//        Mockito.when(stockExchangeService.findStockExchangeByName("abc"))
//                .thenReturn(stockExchangeList);
//
//        mockMvc.perform(get("/exchanges/searchByName?stockExchangeName=abc")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(0));
//    }
//
//    @Test
//    @DisplayName("Find All Stock Exchanges")
//    public void findAllStockExchanges_thenReturnStockExchangesList() throws Exception {
//        List<StockExchange> stockExchangeList = new ArrayList<>();
//        stockExchangeList.add(stockExchange);
//
//        Mockito.when(stockExchangeService.findAllStockExchanges())
//                .thenReturn(stockExchangeList);
//
//        mockMvc.perform(get("/exchanges/")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(1))
//                .andExpect(jsonPath("$[0].id").value(stockExchange.getId()));
//    }
//
//    @Test
//    @DisplayName("Update Stock Exchange By Id with a Valid StockExchange Id")
//    public void updateStockExchange_whenValidId_thenStockExchangeUpdatedReturned() throws Exception {
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
//        Long id =1L;
//
//        Mockito.when(stockExchangeService.updateStockExchange(id,newFields))
//                .thenReturn(newStockExchange);
//
//        mockMvc.perform(put("/exchanges/update?id=1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(newFields)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(newStockExchange.getId()))
//                .andExpect(jsonPath("$.stockExchangeName").value(newStockExchange.getStockExchangeName()))
//                .andExpect(jsonPath("$.brief").value(newStockExchange.getBrief()))
//                .andExpect(jsonPath("$.contactAddress").value(newStockExchange.getContactAddress()))
//                .andExpect(jsonPath("$.remarks").value(newStockExchange.getRemarks()));
//
//    }
//
//    @Test
//    @DisplayName("Update Stock Exchange By Id with a InValid StockExchange Id")
//    public void updateStockExchange_whenInValidId_thenThrowException() throws Exception {
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
//        Long id =2L;
//
//        Mockito.when(stockExchangeService.updateStockExchange(id,newFields))
//                .thenThrow(new StockExchangeIdNotFoundException("Stock Exchange Id Not Available"));
//
//
//        mockMvc.perform(put("/exchanges/update?id=2")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(newFields)))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
//                .andExpect(jsonPath("$.message").value("Stock Exchange Id Not Available"));
//
//    }
//
//}