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
import edu3431.matiukhin.tddlab2.request.ItemPageRequest;
import edu3431.matiukhin.tddlab2.request.ProductRequest;
import edu3431.matiukhin.tddlab2.request.UpdateProductRequest;
import edu3431.matiukhin.tddlab2.response.ApiResponse;
import edu3431.matiukhin.tddlab2.response.BaseMetaData;
import edu3431.matiukhin.tddlab2.response.PaginationMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.*;

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


    //========================
    public ApiResponse<BaseMetaData, ProductRequest> getByIdAsApiResponse(String id) {
        Product productPersisted = itemRepository.findById(id).orElse(null);
        if (productPersisted == null) {
            BaseMetaData meta = new BaseMetaData(404, false);
            meta.setErrorMessage("Not found");
            return new ApiResponse<>(meta, Collections.emptyList());
        }
        ProductRequest productRequest = fromProductToProductRequest(productPersisted);
        BaseMetaData baseMetaData = new BaseMetaData();
        ApiResponse<BaseMetaData, ProductRequest> response = new ApiResponse<>(baseMetaData, productRequest);
        return response;
    }

    public  ApiResponse<BaseMetaData, ProductRequest> getAllAsApiResponse() {
        List<Product> products = itemRepository.findAll();
        List <ProductRequest> productRequestList=products.stream()
                .map(this::fromProductToProductRequest)
                .collect(Collectors.toList());
        BaseMetaData baseMetaData = new BaseMetaData();
        ApiResponse<BaseMetaData, ProductRequest> response = new ApiResponse<>(baseMetaData, productRequestList);
        return response;
    }

    public  ApiResponse<BaseMetaData, ProductRequest> updateAsApiResponse(ProductRequest productRequest) {
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
            ProductRequest productRequestUpdated= fromProductToProductRequest(itemToUpdate);
            BaseMetaData baseMetaData = new BaseMetaData();
            ApiResponse<BaseMetaData, ProductRequest> response = new ApiResponse<>(baseMetaData, productRequestUpdated);
            return response;
        }
        BaseMetaData meta = new BaseMetaData(404, false);
        meta.setErrorMessage("Not found");
        return new ApiResponse<>(meta, Collections.emptyList());
    }




    public ApiResponse<PaginationMetaData, Product> getItemsPage(ItemPageRequest request){

        Pageable pageable = PageRequest.of(request.page(), request.size(),
                Sort.by(Sort.Direction.DESC, "id"));

        Page<Product> page = itemRepository.findAll(pageable);

        PaginationMetaData metaData = new PaginationMetaData();
        metaData.setCode(200);
        // TODO
        metaData.setNumber(page.getNumber());
        metaData.setSize(page.getSize());
        metaData.setTotalPages(page.getTotalPages());
        metaData.setTotalElements(page.getTotalElements());
        metaData.setLast(page.isLast());
        metaData.setFirst(page.isFirst());
        //TODO
        ApiResponse<PaginationMetaData, Product> response =
                new ApiResponse<>(metaData, page.getContent());
        if (page.getTotalPages() > 0 && request.page() >= page.getTotalPages()) {
            metaData.setCode(400);
            metaData.setSuccess(false);
            metaData.setErrorMessage("Warning: page value is out of range");
            return new ApiResponse<>(metaData, page.getContent());
        }
        if(page.isEmpty()){
            metaData.setCode(400);
            metaData.setSuccess(false);
            metaData.setErrorMessage("No items found");
            return new ApiResponse<>(metaData, page.getContent());
        }
        metaData.setCode(200);
        metaData.setSuccess(true);
        metaData.setErrorMessage(null);

        return response;
    }



}