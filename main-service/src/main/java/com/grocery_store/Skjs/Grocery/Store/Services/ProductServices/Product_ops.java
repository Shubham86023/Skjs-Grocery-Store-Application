package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface Product_ops {

    Map<String, Object> commonData();

    ProductDetailRequest getProduct(int id);

    void updateProduct(ProductDetailRequest product);

    int saveProductStep1(ProductStep1Request step1Data);

    boolean updateProductStep1(int id, ProductStep1Request step1Data);

    boolean saveProductStep2(int id, ProductStep2Request step2Data);

    boolean saveProductStep3(MultipartFile[] images, int id);

    boolean saveProductStep4(ProductStep4Request step4Data, int id);

    String getProductDescription(int id);

    Integer getProductId(Integer buserId, Integer cat1Id, Integer cat2Id, Integer cat3Id);

    List<Object[]> getProductsByCategory3Id(int cat3id, int minLimit, int maxLimit);

    List<Object[]> getProductsBySearchKeywordOrCategory2Id(ProductSearchRequest request);

    List<Object[]> getProductsByCategory2Id(int cat2Id, int limit);

    List<Object[]> getUniqueProductsByCategory3Id();

    List<Object[]> getProductDetailWithImage(int productId);
}


