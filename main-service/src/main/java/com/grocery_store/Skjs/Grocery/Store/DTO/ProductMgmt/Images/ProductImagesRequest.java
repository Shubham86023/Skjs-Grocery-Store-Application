package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Images;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImagesRequest {

    private int id;
    private int proId;
    private String mainImage;
    private String image2;
    private String image3;
    private String image4;
    private String image5;

}
