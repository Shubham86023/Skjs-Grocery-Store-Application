package com.skjsgrocerystore.productservice.DAO;

import com.skjsgrocerystore.productservice.Entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

    ProductDetail findTopByBuserIdAndCat1IdAndCat2IdAndCat3IdOrderByProductIdDesc(int buserId, int cat1Id,
                                                                                  int cat2Id,
                                                                                  int cat3Id);

    Optional<ProductDetail> findById(int product_id);

    ProductDetail findByProductId(int productId);

    // used in cartcookie services
    @Query(value = "SELECT p.id,i.main_image,p.product_name,p.your_price FROM tbl_products1 p LEFT JOIN tbl_images i ON i.pro_id=p.id WHERE p.id = :product_id", nativeQuery = true)
    List<Object[]> gettingProductDetailWithImage(@Param("product_id") int product_id);

    @Query(value = "SELECT p.id,p.product_id,p.product_name,p.sale_qty,p.sale_unit,p.your_price,p.status,i.main_image,p.date_time_modified FROM tbl_products1 p LEFT JOIN tbl_images i ON i.pro_id=p.id WHERE p.buser_id = :buserId", nativeQuery = true)
    List<Object[]> gettingProductsdata(@Param("buserId") int buserId);

    // featured category queries
    @Query(value = "select p.id,i.main_image,p3.cat3name,p.product_name,p.your_price,p.mrp,p.sale_qty from tbl_products1 p left JOIN tbl_product_categ3 p3 ON p3.id=p.cat3id left join tbl_images as i on i.pro_id = p.id where p.cat2id = :cat2id limit :limitby", nativeQuery = true)
    List<Object[]> gettingProductBycat2id(@Param("cat2id") int cat2id, @Param("limitby") int limitby);

    @Query(value = "select p.id,i.main_image,p3.cat3name,p.product_name,p.your_price,p.mrp,p.sale_qty from tbl_products1 p left JOIN tbl_product_categ3 p3 ON p3.id=p.cat3id left join tbl_images as i on i.pro_id = p.id where p.cat2id = :cat2id1 or p.cat2id = :cat2id2 limit :limitby", nativeQuery = true)
    List<Object[]> gettingProductBy2cat2id(@Param("cat2id1") int cat2id1, @Param("cat2id2") int cat2id2,
                                           @Param("limitby") int limitby);

    @Query(value = "SELECT p.id,i.main_image,((p.mrp - p.your_price)/p.mrp) as discount,p2.cat2name,p.product_name,p.your_price,p.mrp,p.sale_qty FROM tbl_products1 p JOIN tbl_product_categ2 p2 ON p2.id=p.cat2id left join tbl_images as i on i.pro_id = p.id order by discount desc limit 5", nativeQuery = true)
    List<Object[]> top5HighDiscountProducts();

    // search queries
    @Query(value = "select p.id,i.main_image,p3.cat3name,p.product_name,p.your_price,p.mrp,p.sale_qty from tbl_products1 p left JOIN tbl_product_categ3 p3 ON p3.id=p.cat3id left join tbl_images as i on i.pro_id = p.id where p.cat3id = :cat3id LIMIT :minLimit , :maxLimit", nativeQuery = true)
    List<Object[]> gettingProductBycat3id(int cat3id, @Param("minLimit") int minLimit,
                                          @Param("maxLimit") int maxLimit);

    @Query(value = "select p.id,i.main_image,p3.cat3name,p.product_name,p.your_price,p.mrp,p.sale_qty  from tbl_products1 p left JOIN tbl_product_categ3 p3 ON p3.id=p.cat3id left join tbl_images as i on i.pro_id = p.id WHERE p.product_name LIKE %:header_search% or p3.cat3name LIKE %:header_search% LIMIT :minLimit , :maxLimit", nativeQuery = true)
    List<Object[]> getProductsbySearch(
            @Param("header_search") String header_search,
            @Param("minLimit") int minLimit,
            @Param("maxLimit") int maxLimit);

    @Query(value = "select p.id,i.main_image,p3.cat3name,p.product_name,p.your_price,p.mrp,p.sale_qty from tbl_products1 p left JOIN tbl_product_categ3 p3 ON p3.id=p.cat3id left join tbl_images as i on i.pro_id = p.id WHERE (p.product_name LIKE %:header_search% or p3.cat3name LIKE %:header_search%) and p.cat2id =:header_search_select LIMIT :minLimit , :maxLimit", nativeQuery = true)
    List<Object[]> getProductsbySearchAndcat2Id(
            @Param("header_search") String header_search,
            @Param("header_search_select") int header_search_select,
            @Param("minLimit") int minLimit,
            @Param("maxLimit") int maxLimit);

    @Query(value = "SELECT p.id,i.main_image,p3.cat3name, p.product_name, p.your_price,p.mrp, p.sale_qty FROM tbl_products1 AS p JOIN tbl_product_categ3 AS p3 ON p3.id = p.cat3id LEFT JOIN tbl_images AS i ON i.pro_id = p.id WHERE p.id IN ( SELECT max(pt.id) FROM tbl_products1 as pt GROUP by pt.cat3id ) LIMIT 20", nativeQuery = true)
    List<Object[]> getUniqueProductsOfcat3id();
}
