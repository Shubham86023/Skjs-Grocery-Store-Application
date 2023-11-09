package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCateg1Request {

    private int id;
    private String cat1Name;

}
