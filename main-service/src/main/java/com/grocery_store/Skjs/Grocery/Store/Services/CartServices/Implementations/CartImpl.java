package com.grocery_store.Skjs.Grocery.Store.Services.CartServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo.CartRepository;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.Cart_ops;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CartImpl implements Cart_ops {

    private final CartRepository cart_repo;

    private final HttpSession session;
    private final HttpServletResponse response;

    @Override
    public List<Object[]> getProductsInCartDB(int userId, String userType) {
        return cart_repo.gettingCartProductsdata(userId, userType);
    }

    @Override
    public Map<String, Object> cartData() {
        Map<String, Object> attributes = new HashMap<>();

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check, bis_login_check = false;
        is_login_check = (Boolean) session.getAttribute("is_login");
        bis_login_check = (Boolean) session.getAttribute("bis_login");

        String userType = "";
        int userid = 0;
        boolean is_login = false, bis_login = false;

        if (is_login_check != null && is_login_check) {
            attributes.put("user_name", session.getAttribute("user_name"));
            userType = "user";
            userid = (Integer) session.getAttribute("user_id");
            is_login = true;
        } else if (bis_login_check != null && bis_login_check) {

            attributes.put("buser_name", session.getAttribute("buser_name"));
            userType = "buser";
            userid = (Integer) session.getAttribute("buser_id");
            bis_login = true;
        }

        List<Object[]> LcartProducts = getProductsInCartDB(userid, userType);
        attributes.put("LcartProducts", LcartProducts);
        attributes.put("is_login", is_login);
        attributes.put("bis_login", bis_login);
        attributes.put("cartSizeAfterLogin", LcartProducts.size());

        return attributes;
    }
}
