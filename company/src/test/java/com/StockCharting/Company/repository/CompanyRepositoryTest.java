//package com.StockCharting.Company.repository;
//
//import com.StockCharting.Company.entity.Company;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//class CompanyRepositoryTest {
//
//    @Autowired
//    private CompanyRepository companyRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    Company company;
//
//    @BeforeEach
//    void setUp() {
//        company = Company.builder()
//                .id(1L)
//                .companyName("Telstra")
//                .ceo("Andy Penn")
//                .listedInStockExchange(false)
//                .build();
//
//        entityManager.persist(company);
//
//    }
//
//    @Test
//    @DisplayName("Find Company By Id in Repo with a Valid Company Id")
//    public void findById_whenValidId_thenReturnCompany(){
//        Company found = companyRepository.findById(1L).get();
//        assertEquals(found.getId(),1L);
//    }
//
//    @Test
//    @DisplayName("Find Company By Id in Repo with a InValid Company Id")
//    public void findById_whenInValidId_thenReturnNull(){
//        Optional<Company> found = companyRepository.findById(2L);
//        assertFalse(found.isPresent());
//    }
//
//    @Test
//    @DisplayName("Find Company By Name in Repo with a Valid Company Name")
//    public void findByCompanyNameIgnoreCase_whenValidCompanyName_thenReturnCompany(){
//        Company found = companyRepository.findByCompanyNameIgnoreCase("telstra");
//        assertNotNull(found);
//        assertEquals(found.getCompanyName().toLowerCase(),"telstra");
//    }
//
//    @Test
//    @DisplayName("Find Company By Name in Repo with a InValid Company Name")
//    public void findByCompanyNameIgnoreCase_whenInValidCompanyName_thenReturnNull(){
//        Company found = companyRepository.findByCompanyNameIgnoreCase("tex");
//        assertNull(found);
//    }
//
//    @Test
//    @DisplayName("Save New Company")
//    public void save_thenReturnCompany(){
//        Company company = Company.builder()
//                .companyName("Amazon")
//                .turnover(36000L)
//                .ceo("Bezos")
//                .listedInStockExchange(false)
//                .build();
//
//        Company found = companyRepository.save(company);
//        assertNotNull(found);
//
//        assertEquals(company.getCompanyName(),found.getCompanyName());
//        assertEquals(company.getTurnover(),found.getTurnover());
//        assertEquals(company.getCeo(),found.getCeo());
//        assertEquals(company.getListedInStockExchange(),found.getListedInStockExchange());
//
//    }
//
//    @Test
//    @DisplayName("Find All Companies")
//    public void findAllCompanies_thenReturnCompaniesList(){
//        List<Company> foundList = companyRepository.findAll();
//        assertEquals(foundList.size(),1);
//        assertSame(foundList.get(0),company);
//    }
//
//    @Test
//    @DisplayName("Delete A Company with Valid Company Id")
//    public void deleteCompany_whenValidId_thenDelete(){
//        Long id = 1L;
//        companyRepository.deleteById(id);
//        Optional<Company> found = companyRepository.findById(id);
//
//        assertFalse(found.isPresent());
//    }
//
//    @Test
//    @DisplayName("Delete A Company with InValid Company Id")
//    public void deleteCompany_whenInValidId_thenReturnEmpty(){
//        Long id = 2L;
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,()->{
//            companyRepository.deleteById(id);
//        });
//        Optional<Company> found = companyRepository.findById(id);
//
//        assertFalse(found.isPresent());
//    }
//}