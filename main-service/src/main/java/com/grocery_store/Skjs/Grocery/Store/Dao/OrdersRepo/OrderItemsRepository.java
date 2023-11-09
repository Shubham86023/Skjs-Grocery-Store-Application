package com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo;

import com.grocery_store.Skjs.Grocery.Store.Entities.Orders.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {

    OrderItems findByIdAndSellerPin(int oiid, int pinNo);

    @Query(value = "select p.id,MAX(i.main_image) as main_image,p3.cat3name,p.product_name,p.your_price,p.mrp,p.sale_qty,sum(oi.qty) as qty from tbl_order_items oi left JOIN tbl_products1 p on p.id=oi.product_id JOIN tbl_product_categ3 p3 ON p3.id=p.cat3id left join tbl_images as i on i.pro_id = p.id GROUP by oi.product_id ORDER by qty desc limit 12;", nativeQuery = true)
    List<Object[]> bestSellersProducts();

}
