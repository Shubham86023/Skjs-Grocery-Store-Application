package com.grocery_store.Skjs.Grocery.Store.Services.CartServices;

import com.grocery_store.Skjs.Grocery.Store.DTO.CartProductsInCookie;

import java.util.List;
import java.util.Map;

public interface CartCookies_ops {

    String getCookieValue(String cookieName);

    void settingStringIntoCookie(String data);

    void addUpdate_cartCookie(int pro_id, int qty);

    List<CartProductsInCookie> getProductListInCookie();

    void deleteProductFromCart(int pid);

    void clearingCartCookie();

    List<Object> getCartTotalQtyAndAmount();

    Map<Integer, Integer> getProductMapFromCartCookie();
}
