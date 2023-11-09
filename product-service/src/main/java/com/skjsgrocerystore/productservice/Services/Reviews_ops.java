package com.skjsgrocerystore.productservice.Services;

import com.skjsgrocerystore.productservice.DTO.Reviews.ProductReviewResponse;
import com.skjsgrocerystore.productservice.Entities.Reviews;

import java.util.List;

public interface Reviews_ops {

    List<Object[]> getAllReviewsByProductId(int id);

    List<Object[]> getRatingWiseReviews(int id);

    ProductReviewResponse getUserReview(ProductReviewResponse response);

    Reviews saveUserReview(ProductReviewResponse response);

    List<Object[]> top10HighRatedProducts();

}
