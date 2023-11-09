package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStep4Request {

    private int id;
    private List<String> points;
    private String description;
    private List<Integer> pointIds;
    private String dateTimeModified;

}

