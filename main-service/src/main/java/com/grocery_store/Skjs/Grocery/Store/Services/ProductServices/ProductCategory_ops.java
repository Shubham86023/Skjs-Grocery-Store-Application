package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg1Request;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg2Request;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg3Request;

import java.util.List;

public interface ProductCategory_ops {

    List<ProductCateg1Request> cat1List();

    List<ProductCateg2Request> cat2List();

    List<ProductCateg2Request> cat2List_by_cat1id(int id);

    List<ProductCateg3Request> cat3List_by_cat1_2id(Integer id1, Integer id2);

    ProductCateg3Request getCategory3ById(int id);
}
