package com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo;

import com.grocery_store.Skjs.Grocery.Store.Entities.Orders.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    // for revenue generated,order completed
    @Query(value = "select sum(row_revenue),count(*) from (select sum(oi.price * oi.qty) as row_revenue from tbl_order_items oi left join tbl_orders o on o.id=oi.order_id left join tbl_products1 p on p.id=oi.product_id where oi.seller_delivery_status=1 and p.buser_id=:buserId GROUP by oi.order_id) as squery;", nativeQuery = true)
    List<Object[]> getRevenueGeneratedAndOrderCompleted(@Param("buserId") int buserId);

    // for pending orders
    @Query(value = "SELECT count(*) FROM (select count(oi.order_id) from tbl_order_items oi left join tbl_orders o on o.id=oi.order_id left join tbl_products1 p on p.id=oi.product_id where oi.seller_delivery_status=0 and p.buser_id=:buserId group by oi.order_id) as squery", nativeQuery = true)
    List<Object[]> getPendingOrders(@Param("buserId") int buserId);

    // for total products & amount
    @Query(value = "select COUNT(*) as total_product,sum(p.your_price * p.sale_qty) as total_amount from tbl_products1 p where p.buser_id=:buserId", nativeQuery = true)
    List<Object[]> getTotalAmountAndTotalProduct(@Param("buserId") int buserId);

    // for low stock products
    @Query(value = "SELECT count(*) from tbl_products1 p where p.buser_id=:buserId and p.sale_qty < 5", nativeQuery = true)
    List<Object[]> getLowStockProducts(@Param("buserId") int buserId);

    // for incomplete products
    @Query(value = "SELECT count(*) from tbl_products1 p where p.buser_id=:buserId and p.status=0", nativeQuery = true)
    List<Object[]> getIncompleteProducts(@Param("buserId") int buserId);

    // Sellers Side
    @Query(value = "select o.id,p.id,i.main_image,p.seller_sku,p.product_name,oi.qty,o.order_date,o.user_type,b.moblie_number as b_mob,u.moblie_number as u_mob,a.address,oi.id from tbl_order_items oi left join tbl_orders o on o.id=oi.order_id left join tbl_products1 p on p.id=oi.product_id LEFT JOIN tbl_images i ON i.pro_id=p.id left join tbl_all_user_address a on a.id=o.address_id left JOIN tbl_business_users b on b.id=o.user_id and o.user_type='buser' LEFT JOIN tbl_users u on u.id=o.user_id and o.user_type='user' where p.buser_id=:buserId and oi.seller_delivery_status=0 ORDER by o.id ASC;", nativeQuery = true)
    List<Object[]> ordersPending(@Param("buserId") int buserId);

    @Query(value = "select o.id,p.id,i.main_image,p.seller_sku,p.product_name,oi.qty,oi.seller_delivery_date from tbl_order_items oi left join tbl_orders o on o.id=oi.order_id left join tbl_products1 p on p.id=oi.product_id LEFT JOIN tbl_images i ON i.pro_id=p.id where p.buser_id=:buserId and oi.seller_delivery_status=1 ORDER by o.id ASC", nativeQuery = true)
    List<Object[]> ordersCompleted(@Param("buserId") int buserId);

    // Customer Side
    @Query(value = "select o.id,p.id,i.main_image,p.product_name,oi.qty,oi.price,o.order_date,a.address,oi.seller_pin from tbl_order_items oi left join tbl_orders o on o.id=oi.order_id left join tbl_products1 p on p.id=oi.product_id LEFT JOIN tbl_images i ON i.pro_id=p.id left join tbl_all_user_address a on a.id=o.address_id where o.user_id=:userId and o.user_type=:userType and oi.seller_delivery_status=0 ORDER by o.id ASC", nativeQuery = true)
    List<Object[]> placedInprogressOrders(@Param("userType") String userType, @Param("userId") int userId);

    @Query(value = "select o.id,p.id,i.main_image,p.product_name,oi.qty,oi.price,o.order_date,a.address,oi.seller_delivery_date from tbl_order_items oi left join tbl_orders o on o.id=oi.order_id left join tbl_products1 p on p.id=oi.product_id LEFT JOIN tbl_images i ON i.pro_id=p.id left join tbl_all_user_address a on a.id=o.address_id where o.user_id=:userId and o.user_type=:userType and oi.seller_delivery_status=1 ORDER by o.id ASC", nativeQuery = true)
    List<Object[]> placedDeliveredOrders(@Param("userType") String userType, @Param("userId") int userId);

}
