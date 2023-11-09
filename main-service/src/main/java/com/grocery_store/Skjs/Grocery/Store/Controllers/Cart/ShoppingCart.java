package com.grocery_store.Skjs.Grocery.Store.Controllers.Cart;

import com.grocery_store.Skjs.Grocery.Store.DTO.CartProductsInCookie;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg2Request;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.Cart_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductCategory_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Product_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class ShoppingCart {

    private final ProductCategory_ops productCategoryOps;
    private final Product_ops productOps;
    private final CartCookies_ops cartCookiesOps;
    private final Cart_ops cartOps;

    @ModelAttribute
    public void commonData(Model m) {
        List<ProductCateg2Request> catList = productCategoryOps.cat2List();
        m.addAttribute("categories1", catList);

        // totalqty amount
        List<Object> qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
        m.addAttribute("qtyAmount", qtyAmount);

        // related products
        m.addAttribute("uniqueproduct", productOps.getUniqueProductsByCategory3Id());
    }

    @RequestMapping("/yourCart")
    public String yourCart(Model m) {
        Map<String, Object> attributes = cartOps.cartData();

        List<CartProductsInCookie> allCartProducts = cartCookiesOps.getProductListInCookie();
        attributes.put("cartProducts", allCartProducts);

        if ((allCartProducts.isEmpty()) && ((int) attributes.get("cartSizeAfterLogin") == 0)) {
            attributes.put("isEmpty", true);
        } else {
            attributes.put("isEmpty", false);
        }

        m.addAllAttributes(attributes);
        return "shoppingCart";
    }

}
