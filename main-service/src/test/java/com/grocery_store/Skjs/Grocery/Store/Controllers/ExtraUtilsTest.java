package com.grocery_store.Skjs.Grocery.Store.Controllers;

import com.grocery_store.Skjs.Grocery.Store.Dao.EmailsForNewProduct_Repository;
import com.grocery_store.Skjs.Grocery.Store.Entities.EmailsForNewProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@SpringBootTest
class ExtraUtilsTest {

    @MockBean
    private EmailsForNewProduct_Repository mockRepository;

    @BeforeEach
    public void setUp() {
        // creating mock repo
        //mockRepository = Mockito.mock(EmailsForNewProduct_Repository.class);
    }

    @ParameterizedTest
    @CsvSource({"shu88210@gmail.com, 0", "null, 0", "random, 1", "skjs, 1"})
    //@CsvFileSource(resources = "/TestCases.csv")
    public void savingEmailsForNewProductTest(String mail, Integer expectedResult) {

        // Setting result for mockRepository
        EmailsForNewProduct em = null;
        if (expectedResult == 0) {
            em = new EmailsForNewProduct();
            em.setEmail(mail);
        }
        // setting situation
        Mockito.when(mockRepository.findByEmail(mail)).thenReturn(em);

        // Getting result from real method
        ExtraUtils obj = new ExtraUtils(mockRepository);
        Integer actualResult = obj.savingEmailsForNewProduct(mail);

        verify(mockRepository).findByEmail(mail);
        // Comparison
        assertEquals(expectedResult, actualResult);
    }

}
