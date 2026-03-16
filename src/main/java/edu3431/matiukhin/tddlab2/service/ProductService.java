package edu3431.matiukhin.tddlab2.service;/*
@author sasha
@project SoftwareQual8
@class ProductService
@version 1.0.0
@since 01.05.2025 - 01 - 45
*/



import edu3431.matiukhin.tddlab2.model.Product;
import edu3431.matiukhin.tddlab2.repository.ProductRepository;
import edu3431.matiukhin.tddlab2.request.CreateProductRequest;
import edu3431.matiukhin.tddlab2.request.ProductRequest;
import edu3431.matiukhin.tddlab2.request.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository itemRepository;

    // Optional hardcoded items for testing
    /*public List<Product> items = new ArrayList();
    {
        items.add(new Product("1", "Smartphones and mobile phones", "Smartphone", "Redmi Note 5A", 3333, "00001", "Made in China."));
        items.add(new Product("2", "Furniture", "Office chair", "Art Metal Furniture A-36 black", 4444, "00002", "Just usual office chair"));
        items.add(new Product("3", "Large household appliances", "Fridges", "MPM MPM-46-CJ-01", 3333, "00003", "Refrigerator with freezer compartment"));
    }*/

    /*@PostConstruct
    public void init() {
        this.itemRepository.deleteAll();
        this.itemRepository.saveAll(this.items);
    }*/

    public List<ProductRequest> getAll() {
        List<Product> items = itemRepository.findAll();
        return items.stream()
                .map(this::fromProductToProductRequest)
                .collect(Collectors.toList());
    }

    public ProductRequest getById(String id) {
        Product item = this.itemRepository.findById(id).orElse(null);
        return item != null ? fromProductToProductRequest(item) : null;
    }

    public ProductRequest createItem(CreateProductRequest productRequest) {
        Product itemFromRequest = fromCreateProdutRequstToProduct(productRequest);
              Product savedItem = this.itemRepository.save(itemFromRequest);
        return fromProductToProductRequest(savedItem);  // Return the saved item as DTO
    }

    public ProductRequest updateItem(UpdateProductRequest productRequest) {
        Product itemPersisted = itemRepository.findById(productRequest.id()).orElse(null);
        if (itemPersisted != null) {
            Product itemToUpdate =Product.builder()
                    .id(productRequest.id())
                    .category(productRequest.category())
                    .type(productRequest.type())
                    .name(productRequest.name())
                    .price(productRequest.price())
                    .code(productRequest.code())
                    .description(productRequest.description())
                    .build();
            itemRepository.save(itemToUpdate);
            return fromProductToProductRequest(itemToUpdate);

        }
        return null;

    }

    public void deleteById(String id) {
        this.itemRepository.deleteById(id);
    }

    public ProductRequest fromProductToProductRequest(Product product) {

        return new ProductRequest(
                product.getId(),
                product.getCategory(),
                product.getType(),
                product.getName(),
                product.getPrice(),
                product.getCode(),
                product.getDescription()
        );
    }

    public Product fromCreateProdutRequstToProduct(CreateProductRequest product) {
        return new Product(product.category(), product.type(), product.name(), product.price(), product.code(), product.description());
    }



}