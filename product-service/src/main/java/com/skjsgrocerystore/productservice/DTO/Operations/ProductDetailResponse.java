package com.skjsgrocerystore.productservice.DTO.Operations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailResponse {

    private int id;
    //step1
    private int productId;
    private int buserId;
    private int cat1Id;
    private int cat2Id;
    private int cat3Id;
    private String productName;
    private String brandName;
    private int status;

    //step 2
    private String sellerSku;
    private Double yourPrice;
    private Double listPrice;
    private String saleQty;
    private String saleUnit;
    private String orderableQty;
    private String orderableUnit;
    private Double mrp;

    private String description;
    private String dateTimeCreated;
    private String dateTimeModified;

}

