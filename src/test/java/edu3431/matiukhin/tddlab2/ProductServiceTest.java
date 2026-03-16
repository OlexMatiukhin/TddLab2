package edu3431.matiukhin.tddlab2;


import edu3431.matiukhin.tddlab2.model.Product;
import edu3431.matiukhin.tddlab2.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
@author sasha
@project SoftwareQuality8
@class ProductServiceTest
@version 1.0.0
@since 16.03.2026 - 16 - 12
*/
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {

   @Autowired
    private ProductService underTest;

    List<Product> items = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
    }

    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearsDown(){
    }


    @Test
    void whenGetAllItemsListThenSizeIs30() {
        int size = underTest.getAll().size();
        assertEquals(30, size);
    }




}


