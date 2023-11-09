package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Points;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPointsRequest {

    private int id;
    private int proId;
    private String point;

}
