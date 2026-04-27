package edu3431.matiukhin.tddlab2;/*
@author sasha
@project TddLab2
@class LoggingTest
@version 1.0.0
@since 27.04.2026 - 09 - 28
*/

import edu3431.matiukhin.tddlab2.model.Product;
import edu3431.matiukhin.tddlab2.request.CreateProductRequest;
import edu3431.matiukhin.tddlab2.request.ItemPageRequest;
import edu3431.matiukhin.tddlab2.request.ProductRequest;
import edu3431.matiukhin.tddlab2.request.UpdateProductRequest;
import edu3431.matiukhin.tddlab2.response.ApiResponse;
import edu3431.matiukhin.tddlab2.response.BaseMetaData;
import edu3431.matiukhin.tddlab2.response.PaginationMetaData;
import edu3431.matiukhin.tddlab2.service.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoggingTest {

    @Autowired
    private ProductService underTest;

    @Order(1)
    @Test
    void testLoggingOutputBeforeMethodGetById(CapturedOutput output) {
        // given
        ProductRequest product = underTest.getById("69ef0cafdeef9ebd47b6b7f2");
        //when
        assertNotNull(product);
        // then
        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("ProductService.getById"));
        assertTrue(output.toString().contains("69ef0cafdeef9ebd47b6b7f2"));
    }
    @Order(2)
    @Test
    void testLoggingOutputAfterMethodGetById(CapturedOutput output) {
        ProductRequest item =  underTest.getById("69ef0cafdeef9ebd47b6b7f2");
        assertNotNull(item);
        assertTrue(output.toString().contains("ProductService.getById"));
        assertTrue(output.toString().contains("completed successfully"));
        assertTrue(output.toString().contains("69ef0cafdeef9ebd47b6b7f2"));
        assertTrue(output.toString().contains("Emergency Preparedness Kit"));
    }

    @Order(3)
    @Test
    void testLoggingOutputBeforeMethodGetItemsPage(CapturedOutput output) {
        ItemPageRequest request = new ItemPageRequest(0,5);
        ApiResponse<PaginationMetaData, Product> page =  underTest.getItemsPage(request);
        assertNotNull(page);
        assertTrue(output.toString().contains("ProductService.getItemsPage"));
        assertTrue(output.toString().contains("0"));
        assertTrue(output.toString().contains("5"));
    }

    @Order(4)
    @Test
    void testLoggingOutputAfterMethodGetItemsPage(CapturedOutput output) {
        ItemPageRequest request = new ItemPageRequest(0, 5);

        ApiResponse<PaginationMetaData, Product> page = underTest.getItemsPage(request);

        assertNotNull(page);
        assertTrue(output.toString().contains("ProductService.getItemsPage"));
        assertTrue(output.toString().contains("completed successfully"));
    }
    @Order(5)
    @Test
    void testLoggingOutputBeforeMethodGetAll(CapturedOutput output) {
        var products = underTest.getAll();

        assertNotNull(products);
        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("ProductService.getAll"));
    }

    @Order(6)
    @Test
    void testLoggingOutputAfterMethodGetAll(CapturedOutput output) {
        var products = underTest.getAll();

        assertNotNull(products);
        assertTrue(output.toString().contains("ProductService.getAll"));
        assertTrue(output.toString().contains("completed successfully"));
    }

    @Order(7)
    @Test
    void testLoggingOutputBeforeMethodGetByIdAsApiResponse(CapturedOutput output) {
        ApiResponse<BaseMetaData, ProductRequest> response =
                underTest.getByIdAsApiResponse("69ef0cafdeef9ebd47b6b7f2");

        assertNotNull(response);
        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("ProductService.getByIdAsApiResponse"));
        assertTrue(output.toString().contains("69ef0cafdeef9ebd47b6b7f2"));
    }


    @Order(8)
    @Test
    void testLoggingOutputAfterMethodGetByIdAsApiResponse(CapturedOutput output) {
        ApiResponse<BaseMetaData, ProductRequest> response =
                underTest.getByIdAsApiResponse("69ef0cafdeef9ebd47b6b7f2");

        assertNotNull(response);
        assertTrue(output.toString().contains("ProductService.getByIdAsApiResponse"));
        assertTrue(output.toString().contains("completed successfully"));
        assertTrue(output.toString().contains("69ef0cafdeef9ebd47b6b7f2"));
    }
    @Order(9)
    @Test
    void testLoggingOutputBeforeMethodGetAllAsApiResponse(CapturedOutput output) {
        ApiResponse<BaseMetaData, ProductRequest> response =
                underTest.getAllAsApiResponse();

        assertNotNull(response);
        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("ProductService.getAllAsApiResponse"));
    }
    @Order(10)
    @Test
    void testLoggingOutputAfterMethodGetAllAsApiResponse(CapturedOutput output) {
        ApiResponse<BaseMetaData, ProductRequest> response =
                underTest.getAllAsApiResponse();

        assertNotNull(response);
        assertTrue(output.toString().contains("ProductService.getAllAsApiResponse"));
        assertTrue(output.toString().contains("completed successfully"));
    }
    @Order(11)
    @Test
    void testLoggingOutputBeforeMethodCreateItem(CapturedOutput output) {
        CreateProductRequest request = new CreateProductRequest(
                "Test category",
                "Test type",
                "Test product",
                99.99,
                "TEST-CODE-001",
                "Test description"
        );

        ProductRequest created = underTest.createItem(request);

        assertNotNull(created);
        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("ProductService.createItem"));
        assertTrue(output.toString().contains("Test product"));
    }
    @Order(12)
    @Test
    void testLoggingOutputAfterMethodCreateItem(CapturedOutput output) {
        CreateProductRequest request = new CreateProductRequest(
                "Test category",
                "Test type",
                "Test product after",
                120.00,
                "TEST-CODE-002",
                "Test description"
        );

        ProductRequest created = underTest.createItem(request);

        assertNotNull(created);
        assertTrue(output.toString().contains("ProductService.createItem"));
        assertTrue(output.toString().contains("completed successfully"));
        assertTrue(output.toString().contains("Test product after"));
    }
    @Order(13)
    @Test
    void testLoggingOutputBeforeMethodUpdateItem(CapturedOutput output) {
        UpdateProductRequest request = new UpdateProductRequest(
                "69ef0cafdeef9ebd47b6b7f2",
                "Updated category",
                "Updated type",
                "Updated product",
                150.00,
                "UPDATED-CODE",
                "Updated description"
        );

        ProductRequest updated = underTest.updateItem(request);

        assertNotNull(updated);
        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("ProductService.updateItem"));
        assertTrue(output.toString().contains("69ef0cafdeef9ebd47b6b7f2"));
    }
    @Order(14)
    @Test
    void testLoggingOutputAfterMethodUpdateItem(CapturedOutput output) {
        UpdateProductRequest request = new UpdateProductRequest(
                "69ef0cafdeef9ebd47b6b7f2",
                "Updated category",
                "Updated type",
                "Updated product after",
                175.00,
                "UPDATED-CODE-AFTER",
                "Updated description"
        );

        ProductRequest updated = underTest.updateItem(request);

        assertNotNull(updated);
        assertTrue(output.toString().contains("ProductService.updateItem"));
        assertTrue(output.toString().contains("completed successfully"));
        assertTrue(output.toString().contains("Updated product after"));
    }
    @Order(15)
    @Test
    void testLoggingOutputBeforeMethodUpdateAsApiResponse(CapturedOutput output) {
        ProductRequest request = new ProductRequest(
                "69ef0cafdeef9ebd47b6b7f2",
                "Updated category",
                "Updated type",
                "Updated API product",
                200.00,
                "UPDATED-API-CODE",
                "Updated API description"
        );

        ApiResponse<BaseMetaData, ProductRequest> response =
                underTest.updateAsApiResponse(request);

        assertNotNull(response);
        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("ProductService.updateAsApiResponse"));
        assertTrue(output.toString().contains("69ef0cafdeef9ebd47b6b7f2"));
    }
    @Order(15)
    @Test
    void testLoggingOutputAfterMethodUpdateAsApiResponse(CapturedOutput output) {
        ProductRequest request = new ProductRequest(
                "69ef0cafdeef9ebd47b6b7f2",
                "Updated category",
                "Updated type",
                "Updated API product",
                200.00,
                "UPDATED-API-CODE",
                "Updated API description"
        );


        ApiResponse<BaseMetaData, ProductRequest> response =
                underTest.updateAsApiResponse(request);

        assertNotNull(response);
        assertTrue(output.toString().contains("ProductService.updateAsApiResponse"));
        assertTrue(output.toString().contains("completed successfully"));
    }
    @Order(16)
    @Test
    void testLoggingOutputBeforeMethodDeleteById(CapturedOutput output) {
        CreateProductRequest request = new CreateProductRequest(
                "Delete category",
                "Delete type",
                "Product to delete",
                50.00,
                "DELETE-CODE",
                "Delete description"
        );

        ProductRequest created = underTest.createItem(request);
        assertNotNull(created);

        underTest.deleteById(created.id());

        assertTrue(output.toString().contains("Entering method:"));
        assertTrue(output.toString().contains("ProductService.deleteById"));
        assertTrue(output.toString().contains(created.id()));
    }
    @Order(17)
    @Test
    void testLoggingOutputAfterMethodDeleteById(CapturedOutput output) {
         CreateProductRequest request = new CreateProductRequest(
                "Delete category",
                "Delete type",
                "Product to delete after",
                60.00,
                "DELETE-CODE-AFTER",
                "Delete description"
        );

        ProductRequest created = underTest.createItem(request);
        assertNotNull(created);

        underTest.deleteById(created.id());

        assertTrue(output.toString().contains("ProductService.deleteById"));
        assertTrue(output.toString().contains("completed successfully"));
    }
}




