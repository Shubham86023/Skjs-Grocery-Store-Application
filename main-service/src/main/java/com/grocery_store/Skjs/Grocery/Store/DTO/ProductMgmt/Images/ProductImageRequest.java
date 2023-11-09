package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Images;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImageRequest {

    private Integer productId;
    private Integer imageNo;
    private String imageName;

}
