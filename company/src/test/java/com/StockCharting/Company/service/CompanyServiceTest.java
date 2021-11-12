//package com.StockCharting.Company.service;
//
//import com.StockCharting.Company.entity.Company;
//import com.StockCharting.Company.entity.ErrorMessage;
//import com.StockCharting.Company.exception.CompanyIdNotFoundException;
//import com.StockCharting.Company.repository.CompanyRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
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
//class CompanyServiceTest {
//
//    @Autowired
//    private CompanyService companyService;
//
//    @MockBean
//    private CompanyRepository companyRepository;
//
//    Company company;
//
//    @BeforeEach
//    void setUp() {
//
//        company = Company.builder()
//                .id(1L)
//                .companyName("Telstra")
//                .ceo("Andy Penn")
//                .listedInStockExchange(false)
//                .build();
//    }
//
//    @Test
//    @DisplayName("Find Company By Name with a Valid Company Name")
//    public void findCompanyByName_whenValidCompanyName_thenCompanyShouldFound(){
//        Mockito.when(companyRepository.findByCompanyNameIgnoreCase("telstra"))
//                .thenReturn(company);
//        String companyName = "telstra";
//        Company found = companyService.findCompanyByName(companyName);
//
//        assertEquals(companyName.toLowerCase(),found.getCompanyName().toLowerCase());
//    }
//
//    @Test
//    @DisplayName("Find Company By Name with a InValid Company Name")
//    public void findCompanyByName_whenInValidCompanyName_thenCompanyNotFound(){
//        Mockito.when(companyRepository.findByCompanyNameIgnoreCase("tex"))
//                .thenReturn(null);
//
//        String companyName = "tex";
//        Company found = companyService.findCompanyByName(companyName);
//
//        assertNull(found);
//    }
//
//    @Test
//    @DisplayName("Find Company By Id with a Valid Company Id")
//    public void findCompanyById_whenValidId_thenCompanyFound() throws CompanyIdNotFoundException {
//        Mockito.when(companyRepository.findById(1L))
//                .thenReturn(Optional.ofNullable(company));
//        Long id = 1L;
//        Company found = companyService.findCompanyById(id);
//
//        assertEquals(id,found.getId());
//    }
//
//    @Test
//    @DisplayName("Find Company By Id with a InValid Company Id")
//    public void findCompanyById_whenInValidId_thenThrowException(){
//        Long id = 2L;
//
//        Mockito.when(companyRepository.findById(id))
//                .thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(CompanyIdNotFoundException.class,()->{
//            companyService.findCompanyById(id);
//        });
//        assertEquals(exception.getMessage(),"Company Id Not Available");
//    }
//
//    @Test
//    @DisplayName("Save New Company")
//    public void saveCompany_thenReturnCompany(){
//        Company company = Company.builder()
//                .companyName("Amazon")
//                .turnover(36000L)
//                .ceo("Bezos")
//                .listedInStockExchange(false)
//                .build();
//
//        Mockito.when(companyRepository.save(company))
//                .thenReturn(company);
//
//        Company found = companyService.saveCompany(company);
//        assertSame(found,company);
//    }
//
//    @Test
//    @DisplayName("Update Company By Id with a Valid Company Id")
//    public void updateCompany_whenValidId_thenCompanyUpdatedReturned() throws CompanyIdNotFoundException {
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
//
//        Mockito.when(companyRepository.findById(1L))
//                .thenReturn(Optional.ofNullable(company));
//        Mockito.when(companyRepository.save(newCompany))
//                .thenReturn(newCompany);
//
//        Company updated = companyService.updateCompany(1L,updatedFields);
//        assertSame(updated,newCompany);
//    }
//
//    @Test
//    @DisplayName("Update Company By Id with a InValid Company Id")
//    public void updateCompany_whenInValidId_thenThrowException() throws CompanyIdNotFoundException {
//        Long id = 2L;
//
//        Mockito.when(companyRepository.findById(id))
//                .thenReturn(Optional.empty());
//
//        Company updatedFields = company.builder()
//                .companyName("telstra ltd")
//                .build();
//
//        Exception exception = assertThrows(CompanyIdNotFoundException.class,()->{
//            companyService.updateCompany(id,updatedFields);
//        });
//
//        assertEquals(exception.getMessage(),"Company Id Not Available");
//    }
//
//    @Test
//    @DisplayName("Find All Companies")
//    public void findAllCompanies_thenReturnCompaniesList(){
//        List<Company> companyList = new ArrayList<>();
//        companyList.add(company);
//        Mockito.when(companyRepository.findAll())
//                .thenReturn(companyList);
//
//        List<Company> foundList = companyService.findAllCompanies();
//        assertSame(companyList,foundList);
//    }
//
//    @Test
//    @DisplayName("Delete A Company with Valid Company Id")
//    public void deleteCompany_whenValidId_thenDeleteReturnedString() throws CompanyIdNotFoundException {
//        Mockito.when(companyRepository.findById(1L))
//                .thenReturn(Optional.ofNullable(company));
//
//        Mockito.doNothing().when(companyRepository).deleteById(1L);
//        String found = companyService.deleteCompany(1L);
//
//        Mockito.verify(companyRepository,Mockito.times(1)).deleteById(1L);
//        assertEquals(found,"Company Deleted");
//
//    }
//
//    @Test
//    @DisplayName("Delete A Company with InValid Company Id")
//    public void deleteCompany_whenInValidId_thenThrowException() throws CompanyIdNotFoundException {
//
//        Long id = 2L;
//        Mockito.when(companyRepository.findById(id))
//                .thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(CompanyIdNotFoundException.class,()->{
//            companyService.deleteCompany(id);
//        });
//        assertEquals(exception.getMessage(),"Company Id Not Available");
//    }
//}