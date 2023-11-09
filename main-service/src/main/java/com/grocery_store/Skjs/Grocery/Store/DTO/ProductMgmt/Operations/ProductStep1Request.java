package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStep1Request {

    private int id;
    private int productId;
    private int buserId;
    private int cat1Id;
    private int cat2Id;
    private int cat3Id;
    private String productName;
    private String brandName;
    private int status;
    private String dateTimeCreated;
    private String dateTimeModified;

}

