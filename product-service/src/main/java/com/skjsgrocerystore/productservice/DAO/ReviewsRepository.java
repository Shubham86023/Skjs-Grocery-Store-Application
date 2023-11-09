package com.skjsgrocerystore.productservice.DAO;

import com.skjsgrocerystore.productservice.Entities.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

    Reviews findByUserIdAndProductIdAndReviewerType(int user_id, int productId, String reviewerType);

    @Query(value = "SELECT pr.*,bu.full_name as bUserName,u.full_name as userName FROM tbl_product_reviews pr left JOIN tbl_business_users bu ON bu.id=pr.user_id left JOIN tbl_users u ON u.id=pr.user_id WHERE pr.product_id =:id", nativeQuery = true)
    List<Object[]> getAllReviewsByProductId(int id);

    @Query(value = "SELECT pr.*, COUNT(*) as counting, (pr.rating * COUNT(*)) as multi FROM tbl_product_reviews pr WHERE pr.product_id =:id GROUP BY pr.rating ORDER BY pr.rating DESC", nativeQuery = true)
    List<Object[]> getRatingWiseReviewsByProductId(int id);

    @Query(value = "SELECT p.id,max(i.main_image) as main_image ,pc2.cat2name,p.product_name,p.your_price,p.mrp,count(*) as totalReview,(sum(pr.rating) /count(*)) as finalRating FROM tbl_product_reviews pr join tbl_products1 p on p.id=pr.product_id left join tbl_product_categ2 pc2 on pc2.id=p.cat2id left join tbl_images as i on i.pro_id = p.id GROUP BY pr.product_id order by finalRating desc LIMIT 10", nativeQuery = true)
    List<Object[]> top10HighRatedProducts();
}
