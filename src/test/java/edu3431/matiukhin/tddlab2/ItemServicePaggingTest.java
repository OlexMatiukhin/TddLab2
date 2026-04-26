package edu3431.matiukhin.tddlab2;/*
@author sasha
@project TddLab2
@class ItemServicePaggingTest
@version 1.0.0
@since 27.04.2026 - 00 - 53
*/




import edu3431.matiukhin.tddlab2.model.Product;
import edu3431.matiukhin.tddlab2.repository.ProductRepository;
import edu3431.matiukhin.tddlab2.request.ItemPageRequest;
import edu3431.matiukhin.tddlab2.response.ApiResponse;
import edu3431.matiukhin.tddlab2.response.PaginationMetaData;
import edu3431.matiukhin.tddlab2.service.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemServicePagingTest {


    @Autowired
    private ProductService underTest;
    @Autowired
    private ProductRepository productRepository;

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
    @Order(1)
    @Test
    void whenHappyPathThenOk(){
        // given
        ItemPageRequest request = new ItemPageRequest(0,5);
        // when
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        //then
        assertNotNull(response);
        assertNotNull(response.getMeta());

        assertEquals(200, response.getMeta().getCode());
        assertTrue(response.getMeta().isSuccess());
        assertNull(response.getMeta().getErrorMessage());

        assertEquals(0, response.getMeta().getNumber());
        assertEquals(5, response.getMeta().getSize());
        assertEquals(30, response.getMeta().getTotalElements());
        assertEquals(6, response.getMeta().getTotalPages());
        assertTrue(response.getMeta().isFirst());
        assertFalse(response.getMeta().isLast());

        assertNotNull(response.getData());
        assertFalse(response.getData().isEmpty());
        assertEquals(5, response.getData().size());
        assertEquals("69ee993c3eb75e4acdf63f92", response.getData().get(0).getId());
    }
    @Order(2)
    @Test
    void whenPageValueIsOutOfRangeThenErrorMessageHasTheWarning(){
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertEquals("Warning: page value is out of range", response.getMeta().getErrorMessage());
    }

    @Order(3)
    @Test
    void whenPageValueIsOutOfRangeThenResponseNotNull(){
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertNotNull(response);
    }
    @Order(4)
    @Test
    void whenPageValueIsOutOfRangeThenItHasMetada(){
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertEquals("Warning: page value is out of range", response.getMeta().getErrorMessage());
    }
    @Order(5)
    @Test
    void whenPageValueIsOutOfRangeThenItIsNotSuccesful(){
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertFalse(response.getMeta().isSuccess());
    }
    @Order(6)
    @Test
    void whenPageValueIsOutOfRangeThenStatus404(){
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertEquals(400, response.getMeta().getCode());
    }
    @Order(7)
    @Test
    void whenPageValueIsOutOfRangeThenDataIsEmpty(){
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertEquals("Warning: page value is out of range", response.getMeta().getErrorMessage());
        assertTrue(response.getData().isEmpty());
    }






    @Order(8)
    @Test
    void whenSizeIs_7_AndPageIs_4_ThenIsLast_TrueAndSizeEquals_2(){
        // given
        ItemPageRequest request = new ItemPageRequest(4,7);
        // when
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertFalse(response.getMeta().isFirst());
        assertTrue(response.getMeta().isLast());
        assertEquals(7, response.getMeta().getSize());
        assertEquals(2, response.getData().size());
    }

    @Order(9)
    @Test
    void whenTheListIsEmptyThenErrorMessageHasTheWarning(){
        // your code
        productRepository.deleteAll();
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertNotNull(response);
        assertEquals("No items found", response.getMeta().getErrorMessage());


    }
    @Order(10)
    @Test
    void whenTheListIsEmptyThenResponseStatusIsEmptyTrue(){
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertTrue(response.getData().isEmpty());
    }
    @Order(11)
    @Test
    void whenTheListIsEmptyThenResponseStatusIsNotSuccessfulTrue(){
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertFalse(response.getMeta().isSuccess());
    }
    @Order(12)
    @Test
    void whenTheListIsEmptyThenResponseCode404(){
        ItemPageRequest request = new ItemPageRequest(6, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertFalse(response.getMeta().isSuccess());

        assertEquals(400, response.getMeta().getCode());
    }
    @Order(13)
    @Test
    void whenTheListIsEmptyThenMetadataAndDataAreNotNull(){
        ItemPageRequest request = new ItemPageRequest(0, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertNotNull(response);
    }
    @Order(14)
    @Test
    void whenTheListIsEmptyThenTotalPages0(){
        ItemPageRequest request = new ItemPageRequest(0, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertEquals(response.getMeta().getTotalPages(),0);
    }
    @Order(15)
    @Test
    void whenTheListIsEmptyThenIsFirst(){
        ItemPageRequest request = new ItemPageRequest(0, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertTrue(response.getMeta().isFirst());

    }
    @Order(16)
    @Test
    void whenTheListIsEmptyThenIsLast(){
        ItemPageRequest request = new ItemPageRequest(0, 5);
        ApiResponse<PaginationMetaData, Product> response = underTest.getItemsPage(request);
        assertTrue(response.getMeta().isLast());
    }


}
