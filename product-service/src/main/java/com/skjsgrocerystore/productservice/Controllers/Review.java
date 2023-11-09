package com.skjsgrocerystore.productservice.Controllers;

import com.skjsgrocerystore.productservice.DTO.Reviews.ProductReviewResponse;
import com.skjsgrocerystore.productservice.Entities.Reviews;
import com.skjsgrocerystore.productservice.Exceptions.ResourceNotFoundException;
import com.skjsgrocerystore.productservice.Services.Reviews_ops;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class Review {

    private final Reviews_ops reviewsOps;

    @GetMapping("/getAllReviewsByProductId/{id}")
    public ResponseEntity<List<Object[]>> getAllReviewsByProductId(@PathVariable int id) {

        List<Object[]> response = reviewsOps.getAllReviewsByProductId(id);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Reviews Available.");
        }
    }

    @GetMapping("/getRatingWiseReviews/{id}")
    public ResponseEntity<List<Object[]>> getRatingWiseReviews(@PathVariable int id) {

        List<Object[]> response = reviewsOps.getRatingWiseReviews(id);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Rating data Available.");
        }
    }

    @PostMapping("/getUserReview")
    public ResponseEntity<ProductReviewResponse> getUserReview(@RequestBody @Valid ProductReviewResponse response) {

        response = reviewsOps.getUserReview(response);
        if (response == null) {
            response = new ProductReviewResponse();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/saveUserReview")
    public ResponseEntity<Reviews> saveUserReview(@RequestBody @Valid ProductReviewResponse data) {

        Reviews response = reviewsOps.saveUserReview(data);

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/top10HighRatedProducts")
    public ResponseEntity<List<Object[]>> top10HighRatedProducts() {

        List<Object[]> response = reviewsOps.top10HighRatedProducts();

        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Products Available.");
        }
    }

}
