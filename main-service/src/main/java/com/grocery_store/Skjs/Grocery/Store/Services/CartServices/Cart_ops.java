package com.grocery_store.Skjs.Grocery.Store.Services.CartServices;


import java.util.List;
import java.util.Map;

public interface Cart_ops {

    List<Object[]> getProductsInCartDB(int userId, String userType);

    Map<String, Object> cartData();

}
