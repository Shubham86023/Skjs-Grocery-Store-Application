package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.ProductReviewRequest;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductReviews_ops;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductReviewsImpl implements ProductReviews_ops {

    private final HttpSession session;
    private final RestTemplate restTemplate;

    private final ParameterizedTypeReference<List<Object[]>> responseType
            = new ParameterizedTypeReference<>() {
    };

    private List<Object[]> data = new ArrayList<>();

    @Override
    public List<Object[]> getAllReviewsByProductId(int id) {
        try {
            data = restTemplate.exchange(
                    "http://product-service/reviews/getAllReviewsByProductId/" + id,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- getAllReviewsByProductId");
            System.out.println(ex);
        }
        return data;

    }

    @Override
    public List<Object[]> getRatingWiseReviews(int id) {
        try {
            data = restTemplate.exchange(
                    "http://product-service/reviews/getRatingWiseReviews/" + id,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- getRatingWiseReviews");
            System.out.println(ex);
        }
        return data;
    }

    public ProductReviewRequest getUserReview(ProductReviewRequest reviewReq) {

        ProductReviewRequest data = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<ProductReviewRequest> requestEntity = new HttpEntity<>(reviewReq, headers);

            data = restTemplate.exchange("http://product-service/reviews/getUserReview",
                    HttpMethod.POST, requestEntity, ProductReviewRequest.class).getBody();

        } catch (Exception ex) {
            System.out.println(ex);
            System.out.println("Unable to get Data :- getUserReview");
        }
        return data;

    }

    @Override
    public boolean saveUpdateReview(ProductReviewRequest reviewReq) {

        String reviewerType = "";
        Integer userId = 0;
        Object is_login_check = session.getAttribute("is_login");
        Object bis_login_check = session.getAttribute("bis_login");
        if (is_login_check != null && (boolean) is_login_check) {
            userId = (Integer) session.getAttribute("user_id");
            reviewerType = "user";
        } else if (bis_login_check != null && (boolean) bis_login_check) {
            userId = (Integer) session.getAttribute("buser_id");
            reviewerType = "buser";
        }
        reviewReq.setReviewerType(reviewerType);
        reviewReq.setUserId(userId);

        ProductReviewRequest review = getUserReview(reviewReq);

        if (review != null) {

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            if (review.getId() != 0) {
                review.setCheckbox_result(reviewReq.getCheckbox_result());
                review.setReviewerMessage(reviewReq.getReviewerMessage());
                review.setDate(dateFormat.format(date));

            } else {
                review = ProductReviewRequest.builder()
                        .userId(userId)
                        .productId(reviewReq.getProductId())
                        .checkbox_result(reviewReq.getCheckbox_result())
                        .reviewerType(reviewerType)
                        .reviewerMessage(reviewReq.getReviewerMessage())
                        .date(dateFormat.format(date)).build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            HttpEntity<ProductReviewRequest> requestEntity = new HttpEntity<>(review, headers);

            restTemplate.exchange("http://product-service/reviews/saveUserReview",
                    HttpMethod.POST, requestEntity, ProductReviewRequest.class);

            return true;
        }
        return false;
    }


}
