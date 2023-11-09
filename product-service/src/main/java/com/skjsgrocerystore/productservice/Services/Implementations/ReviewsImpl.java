package com.skjsgrocerystore.productservice.Services.Implementations;

import com.skjsgrocerystore.productservice.DAO.ReviewsRepository;
import com.skjsgrocerystore.productservice.DTO.Reviews.ProductReviewResponse;
import com.skjsgrocerystore.productservice.Entities.Reviews;
import com.skjsgrocerystore.productservice.Services.Reviews_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewsImpl implements Reviews_ops {

    private final ReviewsRepository reviewsRepo;

    @Override
    public List<Object[]> getAllReviewsByProductId(int id) {
        return reviewsRepo.getAllReviewsByProductId(id);
    }

    @Override
    public List<Object[]> getRatingWiseReviews(int id) {

        return reviewsRepo.getRatingWiseReviewsByProductId(id);
    }

    @Override
    public ProductReviewResponse getUserReview(ProductReviewResponse response) {
        Reviews review = reviewsRepo.findByUserIdAndProductIdAndReviewerType(response.getUserId(),
                response.getProductId(), response.getReviewerType());

        if (review != null) {
            return ProductReviewResponse.builder()
                    .id(review.getId())
                    .userId(review.getUserId())
                    .reviewerType(review.getReviewerType())
                    .reviewerMessage(review.getReviewerMessage())
                    .checkbox_result(review.getRating())
                    .productId(review.getProductId())
                    .date(review.getDate()).build();
        }
        return null;
    }

    @Override
    public Reviews saveUserReview(ProductReviewResponse response) {

        Reviews review = new Reviews();
        if (response.getId() != 0) {
            review.setId(response.getId());
        }
        review.setUserId(response.getUserId());
        review.setReviewerType(response.getReviewerType());
        review.setReviewerMessage(response.getReviewerMessage());
        review.setRating(response.getCheckbox_result());
        review.setProductId(response.getProductId());
        review.setDate(response.getDate());

        return reviewsRepo.save(review);
    }

    @Override
    public List<Object[]> top10HighRatedProducts() {
        return reviewsRepo.top10HighRatedProducts();
    }

}
