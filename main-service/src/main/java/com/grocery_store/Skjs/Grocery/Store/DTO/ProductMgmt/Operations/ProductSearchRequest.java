package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchRequest {

    private int isSearch;
    private String srchWithKywrd;
    private Integer srchWithCtgry2;
    private int minLimit;
    private int maxLimit;
}

