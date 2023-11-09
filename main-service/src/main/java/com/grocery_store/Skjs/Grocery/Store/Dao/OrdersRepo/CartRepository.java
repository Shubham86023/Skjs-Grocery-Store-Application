package com.grocery_store.Skjs.Grocery.Store.Dao.OrdersRepo;

import com.grocery_store.Skjs.Grocery.Store.Entities.Orders.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByUserTypeAndUseridAndProductid(String userType, Integer userid, Integer pro_id);

    @Query(value = "SELECT p.id,i.main_image,p.product_name,p.your_price,(c.qty * p.your_price) as row_total,c.*,p.sale_qty FROM tbl_cart c JOIN tbl_products1 p ON p.id= c.productid left join tbl_images as i on i.pro_id= p.id WHERE c.userid=:userId and c.user_type=:userType", nativeQuery = true)
    List<Object[]> gettingCartProductsdata(@Param("userId") int userId, @Param("userType") String userType);

    @Query(value = "select p.id,p.your_price,c.qty from tbl_cart c left join tbl_products1 p on c.productid = p.id WHERE c.userid=:userId and c.user_type=:userType", nativeQuery = true)
    List<Object[]> gettingCartDataForCheckOut(@Param("userId") int userId, @Param("userType") String userType);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tbl_cart WHERE userid=:userid and user_type=:userType", nativeQuery = true)
    void clearingcartData(@Param("userid") int userid, @Param("userType") String userType);
}
