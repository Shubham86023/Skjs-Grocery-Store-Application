package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices;


import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.ProductReviewRequest;

import java.util.List;

public interface ProductReviews_ops {

    //ProductReviews getProductReviewByUserId(int user_id, int productId, String reviewerType);

    boolean saveUpdateReview(ProductReviewRequest reviewReq);

    List<Object[]> getAllReviewsByProductId(int id);

    List<Object[]> getRatingWiseReviews(int id);

}
