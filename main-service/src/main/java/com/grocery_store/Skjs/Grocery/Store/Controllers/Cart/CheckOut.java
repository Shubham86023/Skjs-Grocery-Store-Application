package com.grocery_store.Skjs.Grocery.Store.Controllers.Cart;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductDetailRequest;
import com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo.CartRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo.OrderItemsRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo.OrdersRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.AllUserAddressRepository;
import com.grocery_store.Skjs.Grocery.Store.Entities.Orders.OrderItems;
import com.grocery_store.Skjs.Grocery.Store.Entities.Orders.Orders;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.AllUserAddress;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Product_ops;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
@AllArgsConstructor
public class CheckOut {

    private final Product_ops productOps;
    private final CartCookies_ops cartCookiesOps;

    private final AllUserAddressRepository address_repo;
    private final OrdersRepository order_repo;
    private final OrderItemsRepository orderItem_repo;
    private final CartRepository cart_repo;

    private final HttpSession session;
    private final HttpServletResponse response;
    private final HttpServletRequest request;


    @RequestMapping("/checkingOut")
    public String checkingOut(Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check = false;
        Boolean bis_login_check = false;
        is_login_check = (Boolean) session.getAttribute("is_login");
        bis_login_check = (Boolean) session.getAttribute("bis_login");
        if (is_login_check != null && is_login_check) {
            int userid = (Integer) session.getAttribute("user_id");
            List<AllUserAddress> all_address = address_repo.findByUserIdAndUserType(userid, "user");
            m.addAttribute("userLogin", "user");

            if (!all_address.isEmpty()) {
                m.addAttribute("all_address", all_address);
            } else {
                m.addAttribute("all_address", new ArrayList<>());
            }
            m.addAttribute("is_login", true);
            m.addAttribute("bis_login", false);
        } else if (bis_login_check != null && bis_login_check) {
            int buser_id = (Integer) session.getAttribute("buser_id");
            List<AllUserAddress> all_address = address_repo.findByUserIdAndUserType(buser_id, "buser");
            m.addAttribute("userLogin", "business");
            if (!all_address.isEmpty()) {
                m.addAttribute("all_address", all_address);
            } else {
                m.addAttribute("all_address", new ArrayList<>());
            }
            m.addAttribute("bis_login", true);
            m.addAttribute("is_login", false);
        } else {
            return "redirect:/login";
        }

        // totalqty amount
        double price = Double.parseDouble(request.getParameter("price"));
        List<Object> qtyAmount;
        // direct product buy
        if (price != 0.0) {
            qtyAmount = new ArrayList<>();
            int qty = Integer.parseInt(request.getParameter("qty_select"));
            Double total = price * Double.parseDouble(String.valueOf(qty));
            qtyAmount.add(total);
            qtyAmount.add(1);
        } else {
            // buying from cart
            qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
        }
        m.addAttribute("qtyAmount", qtyAmount);
        return "checkingOut";
    }

    @RequestMapping("/orderPlaced")
    public String orderPlaced(@Param(value = "address_selected") int address_selected, RedirectAttributes ra) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check = false, bis_login_check = false;
        is_login_check = (Boolean) session.getAttribute("is_login");
        bis_login_check = (Boolean) session.getAttribute("bis_login");
        int userid = 0;
        String userType = "";
        if (is_login_check != null && is_login_check) {
            userType = "user";
            userid = (Integer) session.getAttribute("user_id");
        } else if (bis_login_check != null && bis_login_check) {
            userid = (Integer) session.getAttribute("buser_id");
            userType = "buser";
        } else {
            return "redirect:/login";
        }
        // saving into 1st table
        Orders or = new Orders();
        or.setUserId(userid);
        or.setUserType(userType);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        or.setOrderDate(now.format(formatter));
        or.setAddressId(address_selected);
        Orders or_saved = order_repo.save(or);
        Random random = new Random();
        int pinNo = random.nextInt(900000) + 100000;
        // saving cookie product data into 2nd table
        Map<Integer, Integer> productsdata = cartCookiesOps.getProductMapFromCartCookie();
        ProductDetailRequest p;
        OrderItems oi;

        if (!productsdata.isEmpty()) {
            for (Integer pro_id : productsdata.keySet()) {
                oi = new OrderItems();
                p = productOps.getProduct(pro_id);

                oi.setOrderId(or_saved.getId());
                oi.setProductId(pro_id);
                oi.setPrice(p.getYourPrice());
                oi.setQty(productsdata.get(pro_id));
                oi.setSellerPin(pinNo);
                oi.setSellerDeliveryStatus(0);
                orderItem_repo.save(oi);

                // Updating stock
                ProductDetailRequest prod = productOps.getProduct(pro_id);
                double pro_qty = Double.parseDouble(prod.getSaleQty());
                if (pro_qty >= productsdata.get(pro_id)) {
                    Double remaining_qty = pro_qty - Double.parseDouble(String.valueOf(productsdata.get(pro_id)));
                    prod.setSaleQty(String.valueOf(remaining_qty));
                    productOps.updateProduct(prod);
                }

            }
        }
        // saving cart product data into 2nd table
        List<Object[]> cartData = cart_repo.gettingCartDataForCheckOut(userid, userType);
        if (!cartData.isEmpty()) {
            for (Object[] row : cartData) {
                oi = new OrderItems();
                oi.setOrderId(or_saved.getId());
                oi.setProductId((int) row[0]);
                oi.setPrice((Double) row[1]);
                oi.setQty((int) row[2]);
                oi.setSellerPin(pinNo);
                oi.setSellerDeliveryStatus(0);
                orderItem_repo.save(oi);

                // decreasing stock
                ProductDetailRequest prod1 = productOps.getProduct((int) row[0]);
                double pro_qty = Double.parseDouble(prod1.getSaleQty());
                if (pro_qty >= (int) row[2]) {
                    Double remaining_qty = pro_qty - Double.parseDouble(String.valueOf((int) row[2]));
                    prod1.setSaleQty(String.valueOf(remaining_qty));
                    productOps.updateProduct(prod1);
                }

            }
        }
        // clearing cart.
        cartCookiesOps.clearingCartCookie();
        cart_repo.clearingcartData(userid, userType);
        ra.addFlashAttribute("pinNo", pinNo);
        ra.addFlashAttribute("orderPlaced", true);
        return "redirect:/yourCart";
    }

}
