package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Implementations;


import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Images.ProductImagesRequest;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductDetailRequest;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Operations.ProductSearchRequest;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.Get_buserDetail;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class ProductSearchImpl implements ProductSearch_ops {


    private final ProductImage_ops productImageOps;
    private final ProductReviews_ops productReviewsOps;
    private final Product_ops productOps;
    private final ProductCategory_ops productCategoryOps;
    private final ProductPoints_ops productPointsOps;
    private final CartCookies_ops cartCookiesOps;

    private final Get_buserDetail bUserData;

    private final HttpSession session;
    private final HttpServletResponse response;

    private final RestTemplate restTemplate;

    private final ParameterizedTypeReference<List<Object[]>> responseType
            = new ParameterizedTypeReference<>() {
    };

    private List<Object[]> data = new ArrayList<>();

    @Override
    public Map<String, Object> commonData() {
        Map<String, Object> attributes = new HashMap<>();

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check, bis_login_check = false;
        is_login_check = (Boolean) session.getAttribute("is_login");
        bis_login_check = (Boolean) session.getAttribute("bis_login");

        boolean is_login = false, bis_login = false;
        if (is_login_check != null && is_login_check) {
            is_login = true;
            attributes.put("user_name", session.getAttribute("user_name"));

        } else if (bis_login_check != null && bis_login_check) {
            bis_login = true;
            attributes.put("buser_name", session.getAttribute("buser_name"));
        }

        attributes.put("is_login", is_login);
        attributes.put("bis_login", bis_login);

        // sending cart qty & amount
        List<Object> qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
        attributes.put("qtyAmount", qtyAmount);
        attributes.put("categories1", productCategoryOps.cat2List());

        return attributes;
    }

    @Override
    public List<Object[]> getProductListByBuserId(int id) {
        try {
            data = restTemplate.exchange(
                    "http://product-service/operations/gettingProductsData/" + id,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- getProductListByBuserId");
            System.out.println(ex);
        }
        return data;
    }

    @Override
    public Map<String, Object> getSingleProductData(int id) {

        Map<String, Object> attributes = new HashMap<>();

        ProductDetailRequest productData = productOps.getProduct(id);
        attributes.put("productData", productData);

        // images
        List<String> images = new ArrayList<>();
        ProductImagesRequest data_real = productImageOps.getProductImages(id);
        if (data_real != null) {
            if (data_real.getMainImage() != null) {
                images.add(data_real.getMainImage());
            }
            if (data_real.getImage2() != null) {
                images.add(data_real.getImage2());
            }
            if (data_real.getImage3() != null) {
                images.add(data_real.getImage3());
            }
            if (data_real.getImage4() != null) {
                images.add(data_real.getImage4());
            }
            if (data_real.getImage5() != null) {
                images.add(data_real.getImage5());
            }
        }
        attributes.put("imageNames", images);

        // savings
        double savingsPer = ((productData.getMrp() - productData.getYourPrice()) * 100)
                / productData.getMrp();
        int savings2 = (int) Math.round(savingsPer);
        double savings1 = Math.round(productData.getMrp() - productData.getYourPrice());
        attributes.put("savings1", savings1);
        attributes.put("savings2", savings2);

        // related products
        List<Object[]> relatedProducts = productOps.getProductsByCategory2Id(productData.getCat2Id(), 10);
        attributes.put("relatedProducts", relatedProducts);

        // reviews
        int pro_id = productData.getId();
        List<Object[]> reviews = productReviewsOps.getAllReviewsByProductId(pro_id);
        attributes.put("count", reviews.size());
        attributes.put("reviews", reviews);

        // rating
        List<Object[]> ratings = productReviewsOps.getRatingWiseReviews(pro_id);
        long r = 0;
        for (Object[] rate : ratings) {
            r += Long.parseLong(String.valueOf(rate[8]));
        }

        float rateOutOf5 = ((float) r) / ((float) reviews.size());
        rateOutOf5 = Math.round(rateOutOf5 * 10.0f) / 10.0f;
        attributes.put("rateOutOf5", rateOutOf5);
        attributes.put("ratings", ratings);

        // sellername
        attributes.put("sellerName", bUserData.getBuserNameById(productData.getBuserId()));

        return attributes;
    }

    @Override
    public Map<String, Object> getProductDataListByCategory3Id(int cat3Id, int maxLimit) {
        Map<String, Object> attributes = new HashMap<>();

        // sending products data
        attributes.put("activePageNo", (maxLimit / 10));
        int minLimit = maxLimit - 10;
        List<Object[]> productList = productOps.getProductsByCategory3Id(cat3Id, minLimit, maxLimit);

        // Setting bullet points for each product in list
        attributes.put("productList", setPointsForEachProduct(productList));

        // Setting pagination data
        if (maxLimit == 10) {
            session.setAttribute("countp", productList.size());
        }
        int count = (Integer) session.getAttribute("countp");
        int noOfPages = (count / 10) + 1;
        List<Integer> pages = IntStream.rangeClosed(1, noOfPages).boxed().toList();
        attributes.put("noOfPages", pages);

        // sending other data
        attributes.put("cat3row", productCategoryOps.getCategory3ById(cat3Id));
        attributes.put("is_search", false);

        return attributes;
    }

    @Override
    public Map<String, Object> getProductDataListByKeywordSearch(int maxLimit, ProductSearchRequest searchData) {
        Map<String, Object> attributes = new HashMap<>();

        if (searchData.getIsSearch() == 1) {
            session.setAttribute("header_search", searchData.getSrchWithKywrd());
            session.setAttribute("header_search_select", searchData.getSrchWithCtgry2());
        }

        String header_search_set = (String) session.getAttribute("header_search");
        Integer header_search_select_set = (Integer) session.getAttribute("header_search_select");
        attributes.put("header_search", header_search_set);
        attributes.put("header_search_select", header_search_select_set);
        attributes.put("activePageNo", (maxLimit / 10));

        // Sending products data
        List<Object[]> productList;
        ProductSearchRequest req;
        int minLimit = maxLimit - 10;
        if (searchData.getSrchWithCtgry2() == null) {
            req = ProductSearchRequest.builder()
                    .srchWithKywrd(header_search_set)
                    .minLimit(minLimit)
                    .maxLimit(maxLimit)
                    .build();

        } else {
            req = ProductSearchRequest.builder()
                    .srchWithKywrd(header_search_set)
                    .srchWithCtgry2(header_search_select_set)
                    .minLimit(minLimit)
                    .maxLimit(maxLimit)
                    .build();

        }
        productList = productOps.getProductsBySearchKeywordOrCategory2Id(req);


        // Setting bullet points for each product in list
        attributes.put("productList", setPointsForEachProduct(productList));


        // Setting pagination data
        if (searchData.getIsSearch() == 1) {
            session.setAttribute("count", productList.size());
        }
        int count = (Integer) session.getAttribute("count");
        int noOfPages = (count / 10) + 1;
        List<Integer> pages = IntStream.rangeClosed(1, noOfPages).boxed().toList();
        attributes.put("noOfPages", pages);

        // sending other data
        attributes.put("is_search", true);

        return attributes;
    }


    private List<Object[]> setPointsForEachProduct(List<Object[]> productList) {

        List<Object[]> updatedProductList = new ArrayList<>();
        for (Object[] row : productList) {
            Object[] with_points = new Object[8];
            int i = 0;
            for (Object ob : row) {
                with_points[i] = ob;
                i++;
            }
            with_points[7] = productPointsOps.getProductPoints((int) row[0]);
            updatedProductList.add(with_points);
        }

        return updatedProductList;
    }

}
