package com.grocery_store.Skjs.Grocery.Store.Controllers.Cart;

import com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo.OrderItemsRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo.OrdersRepository;
import com.grocery_store.Skjs.Grocery.Store.Entities.Orders.OrderItems;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductCategory_ops;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@AllArgsConstructor
public class OrdersController {

    private final ProductCategory_ops productCategoryOps;
    private final CartCookies_ops cartCookiesOps;

    private final OrdersRepository order_repo;
    private final OrderItemsRepository orderItem_repo;

    private final HttpSession session;
    private final HttpServletResponse response;

    @RequestMapping("/ordersReceived")
    public String ordersReceived(Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean bis_login_check = false;
        bis_login_check = (Boolean) session.getAttribute("bis_login");
        if (bis_login_check != null && bis_login_check) {
            Integer buserId = (Integer) session.getAttribute("buser_id");

            List<Object[]> order_p_data = order_repo.ordersPending(buserId);
            m.addAttribute("ordersPending", order_p_data);

            List<Object[]> order_c_data = order_repo.ordersCompleted(buserId);
            m.addAttribute("ordersCompleted", order_c_data);

            // sending cart qty & amount
            List<Object> qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
            m.addAttribute("qtyAmount", qtyAmount);

            m.addAttribute("bis_login", true);
            m.addAttribute("is_login", false);

            m.addAttribute("buser_name", session.getAttribute("buser_name"));
            m.addAttribute("buserId", buserId);

            m.addAttribute("categories0", productCategoryOps.cat1List());
            m.addAttribute("categories1", productCategoryOps.cat2List());
        } else {
            return "redirect:/login";
        }

        return "ordersReceived";
    }


    @RequestMapping("/placedOrders")
    public String placedOrders(Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check, bis_login_check = false;
        is_login_check = (Boolean) session.getAttribute("is_login");
        bis_login_check = (Boolean) session.getAttribute("bis_login");

        Integer userId;
        String userType;
        if (is_login_check != null && is_login_check) {
            userId = (Integer) session.getAttribute("user_id");
            userType = "user";
            m.addAttribute("bis_login", false);
            m.addAttribute("is_login", true);
            m.addAttribute("user_name", session.getAttribute("user_name"));
            m.addAttribute("userId", userId);
        } else if (bis_login_check != null && bis_login_check) {
            userId = (Integer) session.getAttribute("buser_id");
            userType = "buser";
            m.addAttribute("bis_login", true);
            m.addAttribute("is_login", false);
            m.addAttribute("buser_name", session.getAttribute("buser_name"));
            m.addAttribute("buserId", userId);
        } else {
            return "redirect:/login";
        }
        List<Object[]> order_ip_data = order_repo.placedInprogressOrders(userType, userId);
        m.addAttribute("placedInprogressOrders", order_ip_data);

        List<Object[]> order_d_data = order_repo.placedDeliveredOrders(userType, userId);
        m.addAttribute("placedDeliveredOrders", order_d_data);

        // sending cart qty & amount
        List<Object> qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
        m.addAttribute("qtyAmount", qtyAmount);

        m.addAttribute("categories0", productCategoryOps.cat1List());
        m.addAttribute("categories1", productCategoryOps.cat2List());
        return "placedOrders";
    }

    @RequestMapping("/deliverProduct")
    @ResponseBody
    public Boolean deliverProduct(
            @RequestParam(value = "oiid", defaultValue = "") int oiid,
            @RequestParam(value = "pinNo", defaultValue = "") int pinNo) {

        OrderItems oi = orderItem_repo.findByIdAndSellerPin(oiid, pinNo);
        if (oi != null) {
            oi.setSellerDeliveryStatus(1);
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            oi.setSellerDeliveryDate(now.format(formatter));
            orderItem_repo.save(oi);
            return true;
        }
        return false;
    }
}
