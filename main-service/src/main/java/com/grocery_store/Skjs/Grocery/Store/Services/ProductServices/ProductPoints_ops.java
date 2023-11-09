package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices;


import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Points.ProductPointsRequest;

import java.util.List;

public interface ProductPoints_ops {

    List<ProductPointsRequest> getProductPoints(int id);

    boolean deletePoint(int pointId);

}
