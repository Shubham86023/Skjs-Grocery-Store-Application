package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductsForHomepage_ops;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductsForHomepageImpl implements ProductsForHomepage_ops {

    private final RestTemplate restTemplate;

    private final ParameterizedTypeReference<List<Object[]>> responseType
            = new ParameterizedTypeReference<>() {
    };

    private List<Object[]> data = new ArrayList<>();

    @Override
    public List<Object[]> gettingProductBycat2id(int cat2Id, int limit) {
        try {
            data = restTemplate.exchange(
                    "http://product-service/operations/gettingProductBycat2id/" + cat2Id + "/" + limit,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- gettingProductBycat2id");
            System.out.println(ex);
        }
        return data;
    }

    @Override
    public List<Object[]> gettingProductByTwocat2Ids(int cat2Id1, int cat2Id2, int limit) {
        try {
            data = restTemplate.exchange(
                    "http://product-service/operations/gettingProductByTwocat2Ids/" + cat2Id1 + "/" + cat2Id2 + "/" + limit,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- gettingProductByTwocat2Ids");
            System.out.println(ex);
        }
        return data;
    }

    @Override
    public List<Object[]> top5HighDiscountProducts() {
        try {
            data = restTemplate.exchange(
                    "http://product-service/operations/top5HighDiscountProducts",
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- gettingProductBycat2id");
            System.out.println(ex);
        }
        return data;

    }

    @Override
    public List<Object[]> top10HighRatedProducts() {
        try {
            data = restTemplate.exchange(
                    "http://product-service/reviews/top10HighRatedProducts",
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- top10HighRatedProducts");
            System.out.println(ex);
        }
        return data;
    }

}
