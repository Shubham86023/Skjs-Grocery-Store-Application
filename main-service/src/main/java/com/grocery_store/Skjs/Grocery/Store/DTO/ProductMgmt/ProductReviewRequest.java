package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReviewRequest {

    private int id;
    private Integer userId;
    private Integer productId;
    private Integer checkbox_result;
    private String reviewerMessage;
    private String reviewerType;
    private String date;

}


