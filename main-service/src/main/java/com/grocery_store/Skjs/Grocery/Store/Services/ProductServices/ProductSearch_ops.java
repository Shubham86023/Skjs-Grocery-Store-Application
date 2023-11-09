package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductSearchRequest;

import java.util.List;
import java.util.Map;

public interface ProductSearch_ops {

    Map<String, Object> commonData();

    List<Object[]> getProductListByBuserId(int id);

    Map<String, Object> getSingleProductData(int id);

    Map<String, Object> getProductDataListByCategory3Id(int cat3Id, int maxLimit);

    Map<String, Object> getProductDataListByKeywordSearch(int maxLimit, ProductSearchRequest searchData);

}

