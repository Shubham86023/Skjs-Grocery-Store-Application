package com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStep2Request {

    private int id;
    private String sellerSku;
    private Double yourPrice;
    private Double listPrice;
    private String saleQty;
    private String saleUnit;
    private String orderableQty;
    private String orderableUnit;
    private Double mrp;
    private String dateTimeModified;

}
