package edu3431.matiukhin.tddlab2.controller;/*
@author sasha
@project SoftwareQual8
@class ProductRestController
@version 1.0.0
@since 01.05.2025 - 01 - 43
*/

import edu3431.matiukhin.tddlab2.request.CreateProductRequest;
import edu3431.matiukhin.tddlab2.request.ProductRequest;
import edu3431.matiukhin.tddlab2.request.UpdateProductRequest;
import edu3431.matiukhin.tddlab2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping({"/api/v1/products"})
public class ProductRestController {
    private final ProductService itemService;

    @GetMapping
    public List<ProductRequest> getAllItems() {
        return this.itemService.getAll();
    }

    @GetMapping({"/{id}"})
    public ProductRequest getItemById(@PathVariable String id) {
        return this.itemService.getById(id);
    }

    @PostMapping
    public ProductRequest createItem(@RequestBody CreateProductRequest item) {
        return this.itemService.createItem(item);
    }

    @PutMapping
    public ProductRequest updateItem(@RequestBody UpdateProductRequest item) {
        return this.itemService.updateItem(item);
    }

    @DeleteMapping({"/{id}"})
    public void deleteItem(@PathVariable String id) {
        this.itemService.deleteById(id);
    }


}