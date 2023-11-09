package com.grocery_store.Skjs.Grocery.Store.Controllers.ProductManagement;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg2Request;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg3Request;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Images.ProductImageRequest;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.ProductReviewRequest;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductCategory_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductImage_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductPoints_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductReviews_ops;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProductMiscellaneous {

    private final ProductCategory_ops productCategoryOps;
    private final ProductImage_ops productImageOps;
    private final ProductReviews_ops productReviewsOps;
    private final ProductPoints_ops productPointsOps;

    private final HttpSession session;

    @PostMapping("/getCat2ListHtml")
    public String getCat2ListHtml(@RequestParam(value = "cat1", defaultValue = "") Integer cat1id) {

        StringBuilder htmlList = new StringBuilder();
        htmlList.append("<option value=''>Select Sub-Category</option>");
        for (ProductCateg2Request cat1_obj : productCategoryOps.cat2List_by_cat1id(cat1id)) {
            htmlList.append("<option value='").append(cat1_obj.getId()).append("'>").append(cat1_obj.getCat2Name()).append("</option>");
        }
        return htmlList.toString();
    }

    @PostMapping("/getCat3ListHtml")
    public String getCat3ListHtml(@RequestParam(value = "cat1", defaultValue = "") Integer cat1id,
                                  @RequestParam(value = "cat2", defaultValue = "") Integer cat2id) {

        StringBuilder htmlList = new StringBuilder();
        htmlList.append("<option value=''>Select Product Category</option>");
        for (ProductCateg3Request cat12_obj : productCategoryOps.cat3List_by_cat1_2id(cat1id, cat2id)) {
            htmlList.append("<option value='").append(cat12_obj.getId()).append("'>").append(cat12_obj.getCat3Name()).append("</option>");
        }
        return htmlList.toString();
    }


    @PostMapping("/savingProductReview")
    public Integer savingProductReview(@RequestBody ProductReviewRequest productReview) {

        Boolean is_login_check = (Boolean) session.getAttribute("is_login");
        Boolean bis_login_check = (Boolean) session.getAttribute("bis_login");
        if ((bis_login_check != null && bis_login_check) || (is_login_check != null && is_login_check)) {
            if (productReviewsOps.saveUpdateReview(productReview)) {
                return 1;
            }
        }
        return 0;
    }

    @PostMapping("/removeImage")
    public Integer removeImage(@RequestBody ProductImageRequest productImage) {
        if (productImageOps.updateImages(productImage)) {
            return 1;
        }
        return 0;
    }


    @PostMapping("/removeBpoint")
    public Integer removeBpoint(@RequestParam(value = "pointid", defaultValue = "") Integer pointId) {
        if (productPointsOps.deletePoint(pointId)) {
            return 1;
        }
        return 0;
    }

}
