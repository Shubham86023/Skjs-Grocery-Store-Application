package com.grocery_store.Skjs.Grocery.Store.Controllers.ProductManagement;

import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductSearch_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Product_ops;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
public class ProductListing {

    private final Product_ops productOps;
    private final ProductSearch_ops productSearchOps;
    private final CartCookies_ops cartCookiesOps;

    private final HttpSession session;
    private final HttpServletResponse response;

    @ModelAttribute
    public void commonData(Model m) {
        // sending cart qty & amount
        m.addAttribute("qtyAmount", cartCookiesOps.getCartTotalQtyAndAmount());
        m.addAllAttributes(productOps.commonData()
        );
    }

    @RequestMapping("/catalogue")
    public String catalogue(Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check = false;
        is_login_check = (Boolean) session.getAttribute("bis_login");

        if (is_login_check != null && is_login_check) {

            Integer buserId = (Integer) session.getAttribute("buser_id");
            m.addAttribute("product_list", productSearchOps.getProductListByBuserId(buserId));

            return "business/catalogue";
        }
        return "redirect:/homepage";
    }
}
