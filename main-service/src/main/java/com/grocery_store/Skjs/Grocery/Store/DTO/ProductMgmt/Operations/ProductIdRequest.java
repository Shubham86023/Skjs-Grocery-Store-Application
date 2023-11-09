package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductIdRequest {

    private Integer buserId;
    private Integer cat1Id;
    private Integer cat2Id;
    private Integer cat3Id;
}
