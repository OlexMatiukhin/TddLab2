package edu3431.matiukhin.tddlab2;


import edu3431.matiukhin.tddlab2.model.Product;
import edu3431.matiukhin.tddlab2.repository.ProductRepository;
import edu3431.matiukhin.tddlab2.request.ProductRequest;
import edu3431.matiukhin.tddlab2.response.ApiResponse;
import edu3431.matiukhin.tddlab2.response.BaseMetaData;
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

import static org.junit.jupiter.api.Assertions.*;

/*
@author sasha
@project SoftwareQuality8
@class ProductServiceTest
@version 1.0.0
@since 16.03.2026 - 16 - 12
*/
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {

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
    void whenGetAllItemsListThenSizeIs30() {
        int size = underTest.getAll().size();
        assertEquals(30, size);
    }
 @Order(2)
 @Test
 void whenItemIsPresentThenReturnAsOkApiResponse() {
  // given
  String id = "69b836a5980f82888e38ed52";
  // when
  ProductRequest item = underTest.getById(id);
  ApiResponse<BaseMetaData, ProductRequest> response = underTest.getByIdAsApiResponse(id);
  //then
  assertNotNull(response);
  assertFalse(response.getData().isEmpty());
  assertNotNull(response.getData().get(0));
  assertTrue(response.getMeta().isSuccess());
  assertEquals(200, response.getMeta().getCode());
  assertNull(response.getMeta().getErrorMessage());
  assertEquals(item, response.getData().get(0));
 }
 @Order(3)
 @Test
 void whenItemIsNotPresentThenReturn400ApiResponseCode_404() {
  // given
  String id = "69aeefcbe5c3d";
  // when
  ApiResponse<BaseMetaData, ProductRequest> response = underTest.getByIdAsApiResponse(id);
  //then
  assertNotNull(response);
  assertTrue(response.getData().isEmpty());
  assertFalse(response.getMeta().isSuccess());
  assertEquals(404, response.getMeta().getCode());
  assertNotNull(response.getMeta().getErrorMessage());
  assertEquals("Not found",response.getMeta().getErrorMessage());
 }

 @Order(4)
 @Test
 void whenItemIsPresentThenReturnUpdatedItemAsOkApiResponse() {
  // given
  String id = "69b836a5980f82888e38ed52";
  ProductRequest request = new ProductRequest(
          id,
          "Updated category",
          "Updated type",
          "Updated name",
          999.99,
          "UPDATED-CODE",
          "Updated description"
  );

  // when
  ApiResponse<BaseMetaData, ProductRequest> response = underTest.updateAsApiResponse(request);

  // then
  assertNotNull(response);
  assertNotNull(response.getData());
  assertFalse(response.getData().isEmpty());
  assertNotNull(response.getData().get(0));
  assertTrue(response.getMeta().isSuccess());
  assertEquals(200, response.getMeta().getCode());
  assertNull(response.getMeta().getErrorMessage());
  assertEquals(request, response.getData().get(0));
 }
 @Order(5)
 @Test
 void whenItemIsNotPresentThenReturn400ApiResponseCode404() {
  // given
  ProductRequest request = new ProductRequest(
          "69aeefcbe5c3d",
          "Category",
          "Type",
          "Name",
          100.0,
          "CODE-1",
          "Description"
  );

  // when
  ApiResponse<BaseMetaData, ProductRequest> response = underTest.updateAsApiResponse(request);

  // then
  assertNotNull(response);
  assertTrue(response.getData().isEmpty());
  assertFalse(response.getMeta().isSuccess());
  assertEquals(404, response.getMeta().getCode());
  assertNotNull(response.getMeta().getErrorMessage());
  assertEquals("Not found", response.getMeta().getErrorMessage());
 }

 @Order(6)
 @Test
 void whenItemsArePresentThenReturnAsOkApiResponse() {
  // when
  List<ProductRequest> items = underTest.getAll();
  ApiResponse<BaseMetaData, ProductRequest> response = underTest.getAllAsApiResponse();

  // then
  assertNotNull(response);
  assertNotNull(response.getData());
  assertFalse(response.getData().isEmpty());
  assertNotNull(response.getData().get(0));
  assertTrue(response.getMeta().isSuccess());
  assertEquals(200, response.getMeta().getCode());
  assertNull(response.getMeta().getErrorMessage());
  assertEquals(items, response.getData());
 }
 @Order(7)
 @Test
 void whenItemsAreNotPresentThenReturnOkApiResponseWithEmptyData() {
  productRepository.deleteAll();
  // when
  ApiResponse<BaseMetaData, ProductRequest> response = underTest.getAllAsApiResponse();
  // then
  assertNotNull(response);
  assertNotNull(response.getData());
  assertTrue(response.getData().isEmpty());
  assertTrue(response.getMeta().isSuccess());
  assertEquals(200, response.getMeta().getCode());
  assertNull(response.getMeta().getErrorMessage());
 }



}


