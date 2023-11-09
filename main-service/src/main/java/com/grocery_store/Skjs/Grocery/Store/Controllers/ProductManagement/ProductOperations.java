package com.grocery_store.Skjs.Grocery.Store.Controllers.ProductManagement;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductDetailRequest;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductStep1Request;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductStep2Request;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductStep4Request;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductCategory_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductImage_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductPoints_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Product_ops;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@AllArgsConstructor
public class ProductOperations {

    private final HttpSession session;
    private final HttpServletResponse response;

    private final Product_ops productOps;
    private final ProductImage_ops productImageOps;
    private final ProductPoints_ops productPointsOps;
    private final ProductCategory_ops productCategoryOps;
    private final CartCookies_ops cartCookiesOps;

    @ModelAttribute
    public void commonData(Model m) {
        // sending cart qty & amount
        m.addAttribute("qtyAmount", cartCookiesOps.getCartTotalQtyAndAmount());
        m.addAllAttributes(productOps.commonData());
    }

    @GetMapping("/productStep1")
    public String addProductStep1(Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check = false;
        is_login_check = (Boolean) session.getAttribute("bis_login");
        if (is_login_check != null && is_login_check) {
            m.addAttribute("categories0", productCategoryOps.cat1List());
            return "business/addProductStep1";
        }
        return "redirect:/homepage";
    }

    @PostMapping("/saveProductStep1")
    public String saveProductStep1(@ModelAttribute("ProductStep1") ProductStep1Request product) {

        int id = productOps.saveProductStep1(product);
        if (id != 0) {
            return "redirect:/productStep2/" + id;
        }
        return "business/addProductStep1";
    }


    @GetMapping("/editProductStep1/{id}")
    public String editProductStep1(@PathVariable("id") int id, Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check = false;
        is_login_check = (Boolean) session.getAttribute("bis_login");
        if (is_login_check != null && is_login_check) {

            ProductDetailRequest product = productOps.getProduct(id);
            m.addAttribute("data1step", product);
            m.addAttribute("categories0", productCategoryOps.cat1List());
            m.addAttribute("cat2ByCat1", productCategoryOps.cat2List_by_cat1id(product.getCat1Id()));
            m.addAttribute("cat3ByCat21", productCategoryOps.cat3List_by_cat1_2id(product.getCat1Id(), product.getCat2Id()));

            return "business/editProductStep1";
        }
        return "redirect:/homepage";
    }


    @PostMapping("/updateProductStep1/{id}")
    public String updateProductStep1(@PathVariable("id") int id,
                                     @ModelAttribute("data1step") ProductStep1Request step1Data) {
        boolean status = productOps.updateProductStep1(id, step1Data);
        if (status) {
            return "redirect:/productStep2/" + id;
        }
        return "business/editProductStep1";
    }


    @GetMapping("/productStep2/{id}")
    public String addProductStep2(@PathVariable("id") int id, Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check = false;
        is_login_check = (Boolean) session.getAttribute("bis_login");
        if (is_login_check != null && is_login_check) {
            m.addAttribute("data2step", productOps.getProduct(id));
            m.addAttribute("id", id);

            return "business/addProductStep2";
        }
        return "redirect:/homepage";
    }


    @PostMapping("/saveProductStep2/{id}")
    public String saveProductStep2(@PathVariable("id") int id,
                                   @ModelAttribute("ProductStep2") ProductStep2Request product) {
        boolean status = productOps.saveProductStep2(id, product);
        if (status) {
            return "redirect:/productStep3/" + id;
        }
        return "business/addProductStep2";
    }


    @GetMapping("/productStep3/{id}")
    public String addProductStep3(@PathVariable("id") int id, Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check = false;
        is_login_check = (Boolean) session.getAttribute("bis_login");
        if (is_login_check != null && is_login_check) {

            m.addAttribute("productImages", productImageOps.getProductImages(id));
            m.addAttribute("id", id);

            return "business/addProductStep3";
        }
        return "redirect:/homepage";
    }


    @PostMapping("/saveProductStep3/{id}")
    public String saveProductStep3(@PathVariable("id") int id,
                                   @RequestParam(value = "files", required = false) MultipartFile[] images) {
        boolean status = false;
        try {
            status = productOps.saveProductStep3(images, id);
        } catch (Exception x) {
            System.out.println("Error occur while saving images");
        }
        if (status) {
            return "redirect:/productStep4/" + id;
        }
        return "business/addProductStep3";
    }


    @GetMapping("/productStep4/{id}")
    public String addProductStep4(@PathVariable("id") int id, Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check = false;
        is_login_check = (Boolean) session.getAttribute("bis_login");
        if (is_login_check != null && is_login_check) {

            m.addAttribute("points", productPointsOps.getProductPoints(id));
            m.addAttribute("description", productOps.getProductDescription(id));
            m.addAttribute("id", id);

            return "business/addProductStep4";
        }
        return "redirect:/homepage";
    }

    @PostMapping("/saveProductStep4/{id}")
    public String saveProductStep4(@PathVariable("id") int id,
                                   @ModelAttribute("data4step") ProductStep4Request step4Data) {
        boolean status = productOps.saveProductStep4(step4Data, id);
        if (status) {
            return "redirect:/catalogue";
        }
        return "business/addProductStep4";
    }

}
