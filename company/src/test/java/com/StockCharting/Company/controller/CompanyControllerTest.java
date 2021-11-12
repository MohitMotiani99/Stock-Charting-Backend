//package com.StockCharting.Company.controller;
//
//import com.StockCharting.Company.entity.Company;
//import com.StockCharting.Company.exception.CompanyIdNotFoundException;
//import com.StockCharting.Company.service.CompanyService;
//import org.junit.jupiter.api.BeforeEach;
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
//@WebMvcTest(CompanyController.class)
//class CompanyControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private CompanyService companyService;
//
//    private Company company;
//
//    @BeforeEach
//    void setUp() {
//        company = Company.builder()
//                .id(1L)
//                .companyName("Telstra")
//                .ceo("Andy Penn")
//                .listedInStockExchange(false)
//                .build();
//    }
//
//    @Test
//    @DisplayName("Save New Company")
//    public void saveCompany_thenReturnCompany() throws Exception {
//        Company company = Company.builder()
//                .companyName("Amazon")
//                .turnover(36000L)
//                .ceo("Bezos")
//                .listedInStockExchange(false)
//                .build();
//
//        Mockito.when(companyService.saveCompany(company))
//                .thenReturn(company);
//
//        mockMvc.perform(post("/companies/save")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        "\t\"companyName\":\"Amazon\",\n" +
//                        "    \"turnover\":36000,\n" +
//                        "    \"ceo\":\"Bezos\",\n" +
//                        "    \"listedInStockExchange\":false\n" +
//                        "}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.companyName").value("Amazon"));
//
//    }
//
//    @Test
//    @DisplayName("Find Company By Id with a Valid Company Id")
//    public void findCompanyById_whenValidId_thenCompanyFound() throws Exception {
//        Mockito.when(companyService.findCompanyById(1L))
//                .thenReturn(company);
//
//        mockMvc.perform(get("/companies/search?id=1")
//        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id")
//                            .value(company.getId()));
//    }
//
//    @Test
//    @DisplayName("Find Company By Id with a InValid Company Id")
//    public void findCompanyById_whenInValidId_thenThrowException() throws Exception {
//        Mockito.when(companyService.findCompanyById(2L))
//                .thenThrow(new CompanyIdNotFoundException("Company Id Not Available"));
//
//        mockMvc.perform(get("/companies/search?id=2")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status")
//                        .value("NOT_FOUND"))
//                .andExpect(jsonPath("$.message")
//                        .value("Company Id Not Available"));
//    }
//
//    @Test
//    @DisplayName("Find Company By Name with a Valid Company Name")
//    void findCompanyByName_whenValidCompanyName_thenCompanyShouldFound() throws Exception {
//        Mockito.when(companyService.findCompanyByName("Telstra"))
//                .thenReturn(company);
//
//        mockMvc.perform(get("/companies/searchByName?companyName=Telstra")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.companyName")
//                            .value("Telstra"));
//    }
//
//    @Test
//    @DisplayName("Find Company By Name with a InValid Company Name")
//    void findCompanyByName_whenInValidCompanyName_thenCompanyNotFound() throws Exception {
//        Mockito.when(companyService.findCompanyByName("tex"))
//                .thenReturn(null);
//
//        mockMvc.perform(get("/companies/searchByName?companyName=tex")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").doesNotExist());
//    }
//
//    @Test
//    @DisplayName("Update Company By Id with a Valid Company Id")
//    public void updateCompany_whenValidId_thenCompanyUpdatedReturned() throws Exception {
//
//        Company  newCompany = Company.builder()
//                .id(1L)
//                .companyName("telstra ltd")
//                .ceo("Andy Penn")
//                .listedInStockExchange(false)
//                .build();
//
//        Company updatedFields = company.builder()
//                .companyName("telstra ltd")
//                .build();
//
//        Mockito.when(companyService.updateCompany(1L,updatedFields))
//                .thenReturn(newCompany);
//
//        mockMvc.perform(put("/companies/update?id=1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        "    \"companyName\":\"telstra ltd\"\n" +
//                        "}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id")
//                        .value(newCompany.getId()))
//                .andExpect(jsonPath("$.companyName")
//                        .value(newCompany.getCompanyName()));
//    }
//
//    @Test
//    @DisplayName("Update Company By Id with a InValid Company Id")
//    public void updateCompany_whenInValidId_thenThrowException() throws Exception {
//
//        Company updatedFields = company.builder()
//                .companyName("telstra ltd")
//                .build();
//
//        Mockito.when(companyService.updateCompany(2L,updatedFields))
//                .thenThrow(new CompanyIdNotFoundException("Company Id Not Available"));
//
//        mockMvc.perform(put("/companies/update?id=2")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        "    \"companyName\":\"telstra ltd\"\n" +
//                        "}"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status")
//                        .value("NOT_FOUND"))
//                .andExpect(jsonPath("$.message")
//                        .value("Company Id Not Available"));
//    }
//
//    @Test
//    @DisplayName("Find All Companies")
//    public void findAllCompanies_thenReturnCompaniesList() throws Exception {
//        List<Company> companyList = new ArrayList<>();
//        companyList.add(company);
//        Mockito.when(companyService.findAllCompanies())
//                .thenReturn(companyList);
//
//        mockMvc.perform(get("/companies/")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()")
//                            .value(1))
//                .andExpect(jsonPath("$[0].id")
//                            .value(1L));
//    }
//
//    @Test
//    @DisplayName("Delete A Company with Valid Company Id")
//    public void deleteCompany_whenValidId_thenDeleteReturnedString() throws Exception {
//        Mockito.when(companyService.deleteCompany(1L))
//                .thenReturn("Company Deleted");
//
//        mockMvc.perform(delete("/companies/delete?id=1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").value("Company Deleted"));
//    }
//
//    @Test
//    @DisplayName("Delete A Company with InValid Company Id")
//    public void deleteCompany_whenInValidId_thenThrowException() throws Exception {
//        Mockito.when(companyService.deleteCompany(2L))
//                .thenThrow(new CompanyIdNotFoundException("Company Id Not Available"));
//
//        mockMvc.perform(delete("/companies/delete?id=2")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status")
//                        .value("NOT_FOUND"))
//                .andExpect(jsonPath("$.message")
//                        .value("Company Id Not Available"));
//
//
//    }
//
//}