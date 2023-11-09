package com.grocery_store.Skjs.Grocery.Store.Services;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg2Request;
import com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo.OrderItemsRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo.OrdersRepository;
import com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Login_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductCategory_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductsForHomepage_ops;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class DataForHomepagesImpl implements DataForHomepages {

    private final ProductCategory_ops productCategoryOps;
    private final CartCookies_ops cartCookiesOps;
    private final ProductsForHomepage_ops productsForHomepageOps;

    private final Login_ops loginOps;
    private final OrdersRepository order_repo;
    private final OrderItemsRepository orderItem_repo;

    private final HttpSession session;

    @Override
    public Map<String, Object> provideHomepageData() {

        List<Object> qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
        List<ProductCateg2Request> catList = productCategoryOps.cat2List();
        List<Object[]> fruitAndVeggies = productsForHomepageOps.gettingProductBycat2id(1, 10);
        List<Object[]> beverages = productsForHomepageOps.gettingProductBycat2id(4, 10);
        List<Object[]> bathing = productsForHomepageOps.gettingProductByTwocat2Ids(5, 6, 10);
        List<Object[]> beauti = productsForHomepageOps.gettingProductByTwocat2Ids(7, 8, 10);
        List<Object[]> cleaning = productsForHomepageOps.gettingProductByTwocat2Ids(9, 10, 10);
        List<Object[]> highRated = productsForHomepageOps.top10HighRatedProducts();
        List<Object[]> highDiscount = productsForHomepageOps.top5HighDiscountProducts();
        List<Object[]> best_s_pro = orderItem_repo.bestSellersProducts();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String tomorrowDate = sdf.format(calendar.getTime());

        Map<String, Object> attributes = new HashMap<>();

        // Adding page attributes (data)
        attributes.put("qtyAmount", qtyAmount);
        attributes.put("tomorrowDate", tomorrowDate);
        attributes.put("categories1", catList);
        attributes.put("fruitAndVeggies", fruitAndVeggies);
        attributes.put("beverages", beverages);
        attributes.put("bathing", bathing);
        attributes.put("beauti", beauti);
        attributes.put("cleaning", cleaning);
        attributes.put("highDiscount", highDiscount);
        attributes.put("highRated", highRated);
        attributes.put("bestSellersProducts", best_s_pro);

        // Adding login attributes (data)
        boolean bis_login = false;
        boolean is_login = false;

        String loginType = loginOps.loginType();
        if (loginType.equals("welcome")) {
            is_login = true;
            attributes.put("user_name", session.getAttribute("user_name"));
            attributes.put("loginType", loginType);
        }
        attributes.put("bis_login", bis_login);
        attributes.put("is_login", is_login);

        return attributes;
    }

    @Override
    public Map<String, Object> provideBusinessDashboardData() {
        // sending cart qty & amount
        List<Object> qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
        Integer buserId = (Integer) session.getAttribute("buser_id");

        // getting dashboard data
        List<Object[]> data = order_repo.getRevenueGeneratedAndOrderCompleted(buserId);
        List<Object[]> data1 = order_repo.getTotalAmountAndTotalProduct(buserId);
        List<Object[]> data2 = order_repo.getLowStockProducts(buserId);
        List<Object[]> data3 = order_repo.getIncompleteProducts(buserId);
        List<Object[]> data4 = order_repo.getPendingOrders(buserId);

        Map<String, Object> attributes = new HashMap<>();

        // Adding page attributes (data)
        attributes.put("qtyAmount", qtyAmount);
        attributes.put("categories1", productCategoryOps.cat2List());
        attributes.put("revenueGenerated", data.get(0)[0]);
        attributes.put("orderServed", data.get(0)[1]);
        attributes.put("totalProducts", data1.get(0)[0]);
        attributes.put("totalAmount", data1.get(0)[1]);
        attributes.put("lowStockProducts", data2.get(0)[0]);
        attributes.put("incompleteProducts", data3.get(0)[0]);
        attributes.put("pendingOrders", data4.get(0)[0]);

        // Adding login attributes (data)
        attributes.put("bis_login", true);
        attributes.put("is_login", false);
        attributes.put("buser_name", session.getAttribute("buser_name"));
        attributes.put("buserId", buserId);

        return attributes;
    }
}
