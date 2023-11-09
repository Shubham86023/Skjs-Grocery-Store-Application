package com.grocery_store.Skjs.Grocery.Store.Controllers.ProductManagement;


import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductSearchRequest;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductSearch_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@AllArgsConstructor
public class ProductSearch {

    private final ProductSearch_ops productSearchOps;

    @ModelAttribute
    public void commonData(Model m) {
        m.addAllAttributes(productSearchOps.commonData());
    }

    @GetMapping("/products/{id}/{maxLimit}")
    public String getProductBycat3id(@PathVariable("id") int cat3id,
                                     @PathVariable("maxLimit") int maxLimit, Model m) {
        Map<String, Object> attributes = productSearchOps.getProductDataListByCategory3Id(cat3id, maxLimit);
        m.addAllAttributes(attributes);
        return "productListUser";
    }

    @RequestMapping("/searchResults/{maxLimit}")
    public String getProductsBySearch(@PathVariable("maxLimit") int maxLimit,
                                      @ModelAttribute("searchData") ProductSearchRequest searchData,
                                      Model m) {
        Map<String, Object> attributes = productSearchOps.getProductDataListByKeywordSearch(maxLimit, searchData);
        m.addAllAttributes(attributes);
        return "productListUser";
    }

    @GetMapping("/productDetail/{id}")
    public String productDetail(@PathVariable("id") int id, Model m) {
        Map<String, Object> attributes = productSearchOps.getSingleProductData(id);
        m.addAllAttributes(attributes);
        return "singleProduct";
    }

}
