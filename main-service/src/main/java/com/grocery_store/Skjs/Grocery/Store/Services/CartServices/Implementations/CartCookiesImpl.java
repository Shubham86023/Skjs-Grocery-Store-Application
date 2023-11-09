package com.grocery_store.Skjs.Grocery.Store.Services.CartServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.DTO.CartProductsInCookie;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductDetailRequest;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.Cart_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Product_ops;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CartCookiesImpl implements CartCookies_ops {

    private final Product_ops productOps;
    private final Cart_ops cartOps;

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final HttpSession session;

    @Override
    public List<Object> getCartTotalQtyAndAmount() {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check, bis_login_check = false;

        is_login_check = (Boolean) session.getAttribute("is_login");
        bis_login_check = (Boolean) session.getAttribute("bis_login");

        Double totalAmount = 0.0;
        int totalQty = 0, userId = 0;
        String userType = "";
        if (is_login_check != null && is_login_check) {
            userId = (Integer) session.getAttribute("user_id");
            userType = "user";

        } else if (bis_login_check != null && bis_login_check) {
            userId = (Integer) session.getAttribute("buser_id");
            userType = "buser";
        }

        // getting cart quantity and amount from DB
        List<Object[]> bUserData = cartOps.getProductsInCartDB(userId, userType);
        if (bUserData != null) {
            for (Object[] row : bUserData) {
                totalAmount += (Double) row[4];
                totalQty += 1;
            }
        }

        // getting cart quantity and amount from Cookies
        Map<Integer, Integer> productsdata = getProductMapFromCartCookie();
        for (int pro_id : productsdata.keySet()) {
            totalQty += 1;
            ProductDetailRequest a = productOps.getProduct(pro_id);
            Double price = a.getYourPrice();
            Double qty = Double.parseDouble(String.valueOf(productsdata.get(pro_id)));
            totalAmount += price * qty;

        }
        List<Object> data = new ArrayList<>();
        data.add(totalAmount);
        data.add(totalQty);
        return data;
    }


    @Override
    public String getCookieValue(String cookieName) {

        String value = "";
        try {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    value = cookie.getValue();
                    break;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No Items in Cart");
        }
        return value;

    }

    @Override
    public void settingStringIntoCookie(String data) {
        Cookie cookie = new Cookie("cartProductIds", data);
        cookie.setMaxAge(3600 * 24 * 7); // in seconds
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /* add or updating a product in cart */
    @Override
    public void addUpdate_cartCookie(int pro_id, int qty) {

        Map<Integer, Integer> productsdata = new HashMap<>();

        String cookieVal = getCookieValue("cartProductIds");
        if (cookieVal.isEmpty()) {
            productsdata.put(pro_id, qty);
        } else {
            productsdata = getProductMapFromCartCookie();

            if (productsdata.containsKey(pro_id)) {
                // int keyValue = productsdata.get(pro_id);
                productsdata.put(pro_id, qty);
            } else {
                productsdata.put(pro_id, qty);
            }
        }
        String cartJson = String.valueOf(productsdata);
        String cartJson1 = cartJson.replace(" ", "");
        String cartJson2 = cartJson1.replace(",", "/");
        settingStringIntoCookie(cartJson2);
    }


    /* Fetching all cart products */
    @Override
    public List<CartProductsInCookie> getProductListInCookie() {

        Map<Integer, Integer> productsdata = getProductMapFromCartCookie();

        List<CartProductsInCookie> allCartProducts = new ArrayList<>();
        for (Integer pro_id : productsdata.keySet()) {
            CartProductsInCookie producData = new CartProductsInCookie();
            List<Object[]> pro = productOps.getProductDetailWithImage(pro_id);
            for (Object[] element : pro) {
                producData.setProductId((int) element[0]);
                producData.setImagesPath((String) element[1]);
                producData.setProductName((String) element[2]);
                producData.setSellerPrice((Double) element[3]);
                producData.setQty(productsdata.get(pro_id));
            }
            allCartProducts.add(producData);
        }
        return allCartProducts;
    }

    /* Deleting a product from cart */
    @Override
    public void deleteProductFromCart(int pid) {

        Map<Integer, Integer> productsdata = getProductMapFromCartCookie();
        productsdata.remove(pid);
        String cartJson = String.valueOf(productsdata);
        String cartJson1 = cartJson.replace(" ", "");
        String cartJson2 = cartJson1.replace(",", "/");
        settingStringIntoCookie(cartJson2);

    }

    @Override
    public void clearingCartCookie() {
        Map<Integer, Integer> productsData = getProductMapFromCartCookie();
        productsData.clear();
        String cartJson = String.valueOf(productsData);
        settingStringIntoCookie(cartJson);
    }


    /* Helper function */
    @Override
    public Map<Integer, Integer> getProductMapFromCartCookie() {
        Map<Integer, Integer> productsData = new HashMap<>();
        String cookieVal = getCookieValue("cartProductIds");
        if (!cookieVal.isEmpty()) {
            String breaking = cookieVal.substring(1, cookieVal.length() - 1);
            if (!breaking.isEmpty()) {
                String[] pairs = breaking.split("/");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    int key = Integer.parseInt(keyValue[0]);
                    int value = Integer.parseInt(keyValue[1]);
                    productsData.put(key, value);
                }
            }
        }
        return productsData;
    }


}
