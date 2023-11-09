package com.grocery_store.Skjs.Grocery.Store.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductsInCookie {

    private int productId;
    private String imagesPath;
    private String productName;
    private Double sellerPrice;
    private int qty;

}
