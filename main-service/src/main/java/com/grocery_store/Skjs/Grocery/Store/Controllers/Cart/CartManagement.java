package com.grocery_store.Skjs.Grocery.Store.Controllers.Cart;

import com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo.CartRepository;
import com.grocery_store.Skjs.Grocery.Store.Entities.Orders.Cart;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.Implementations.CartCookiesImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class CartManagement {

    private final CartCookiesImpl add_upCart;
    private final CartRepository cart_repo;

    private final HttpSession session;
    private final HttpServletResponse response;

    @RequestMapping("/shoppingCart/{id}/{qty}")
    public String addinCart(@PathVariable("id") Integer pro_id, @PathVariable("qty") Integer qty) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check, bis_login_check = false;
        is_login_check = (Boolean) session.getAttribute("is_login");
        bis_login_check = (Boolean) session.getAttribute("bis_login");

        if (is_login_check != null && is_login_check) {

            int userid = (Integer) session.getAttribute("user_id");
            Cart cUser = cart_repo.findByUserTypeAndUseridAndProductid("user", userid, pro_id);
            if (cUser == null) {
                cUser = Cart.builder().qty(qty).build();
            } else {
                cUser.setQty(cUser.getQty() + qty);
            }
            cUser.setProductid(pro_id);
            cUser.setUserType("user");
            cUser.setUserid(userid);
            cart_repo.save(cUser);

        } else if (bis_login_check != null && bis_login_check) {
            int buser_id = (Integer) session.getAttribute("buser_id");
            Cart bUser = cart_repo.findByUserTypeAndUseridAndProductid("buser", buser_id, pro_id);
            if (bUser == null) {
                bUser = Cart.builder().qty(qty).build();
            } else {
                bUser.setQty(bUser.getQty() + qty);
            }
            bUser.setProductid(pro_id);
            bUser.setUserType("buser");
            bUser.setUserid(buser_id);
            cart_repo.save(bUser);

        } else {
            add_upCart.addUpdate_cartCookie(pro_id, qty);
        }
        return "redirect:/yourCart";
    }

    @RequestMapping("/deleteProductFromCart")
    @ResponseBody
    public String deleteProductFromCart(@RequestParam(value = "pid", defaultValue = "") Integer pid,
                                        @RequestParam(value = "userType", defaultValue = "") String userType) {

        if (userType.equals("cookie")) {
            add_upCart.deleteProductFromCart(pid);
        } else {
            cart_repo.deleteById(pid);
        }
        return "true";
    }

    @RequestMapping("/updateProductFromCart")
    @ResponseBody
    public String updateProductFromCart(@RequestParam(value = "pid", defaultValue = "") Integer pid,
                                        @RequestParam(value = "userType", defaultValue = "") String userType,
                                        @RequestParam(value = "qty", defaultValue = "") Integer qty) {
        if (userType.equals("cookie")) {
            add_upCart.addUpdate_cartCookie(pid, qty);
        } else {
            Optional<Cart> pro = cart_repo.findById(pid);
            if (pro.isPresent()) {
                Cart a = pro.get();
                a.setQty(qty);
                cart_repo.save(a);
            }
        }
        return "true";
    }
}
