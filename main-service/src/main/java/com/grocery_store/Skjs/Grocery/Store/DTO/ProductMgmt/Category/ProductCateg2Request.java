package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCateg2Request {

    private int id;
    private int cat1Id;
    private String cat2Name;

}
