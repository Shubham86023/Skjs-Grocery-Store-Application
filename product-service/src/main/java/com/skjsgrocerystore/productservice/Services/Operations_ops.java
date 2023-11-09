package com.skjsgrocerystore.productservice.Services;

import com.skjsgrocerystore.productservice.DTO.Operations.*;

import java.util.List;


public interface Operations_ops {

    ProductDetailResponse getProduct(int id);

    void updateProduct(int id, ProductDetailResponse response);

    Integer getLatestProductId(ProductIdResponse response);

    Integer saveProductStep1(ProductStep1Response response);

    Integer updateProductStep1(ProductStep1Response response);

    Integer updateProductStep2(ProductStep2Response response);

    Integer updateProductStep4(ProductStep4Response response);

    List<Object[]> getProductsByCategory3Id(int cat3id, int minLimit, int maxLimit);

    List<Object[]> getProductsBySearch(ProductSearchResponse response);

    List<Object[]> getProductsByCategory2Id(int cat2Id, int limit);

    List<Object[]> getUniqueProductsByCategory3Id();

    List<Object[]> getProductDetailWithImage(int id);

    List<Object[]> gettingProductsData(int id);

    List<Object[]> gettingProductBycat2id(int cat2Id, int limit);

    List<Object[]> gettingProductByTwocat2Ids(int cat2Id1, int cat2Id2, int limit);

    List<Object[]> top5HighDiscountProducts();

}


