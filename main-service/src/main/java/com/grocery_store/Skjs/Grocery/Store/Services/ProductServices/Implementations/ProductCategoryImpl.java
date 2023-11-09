package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg1Request;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg2Request;
import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Category.ProductCateg3Request;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductCategory_ops;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductCategoryImpl implements ProductCategory_ops {

    private final RestTemplate restTemplate;


    @Override
    public List<ProductCateg1Request> cat1List() {
        ParameterizedTypeReference<List<ProductCateg1Request>> responseType = new ParameterizedTypeReference<>() {
        };

        List<ProductCateg1Request> data = new ArrayList<>();
        try {
            ResponseEntity<List<ProductCateg1Request>> response = restTemplate.exchange(
                    "http://product-service/category/getCategory1",
                    HttpMethod.GET, null, responseType);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                data.add(ProductCateg1Request.builder().id(0).cat1Name("No Category Available.").build());
            } else {
                data = response.getBody();
            }

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- cat1List");
            System.out.println(ex);
        }
        return data;

    }

    @Override
    public List<ProductCateg2Request> cat2List() {

        ParameterizedTypeReference<List<ProductCateg2Request>> responseType = new ParameterizedTypeReference<>() {
        };
        List<ProductCateg2Request> data = new ArrayList<>();

        try {
            data = restTemplate.exchange(
                    "http://product-service/category/getCategory2",
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- cat2List");
            System.out.println(ex);
        }
        return data;
    }

    @Override
    public List<ProductCateg2Request> cat2List_by_cat1id(int id) {

        ParameterizedTypeReference<List<ProductCateg2Request>> responseType = new ParameterizedTypeReference<>() {
        };
        List<ProductCateg2Request> data = new ArrayList<>();

        try {
            data = restTemplate.exchange(
                    "http://product-service/category/getCategory2/" + id,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- cat2List_by_cat1id");
            System.out.println(ex);
        }
        return data;
    }

    @Override
    public List<ProductCateg3Request> cat3List_by_cat1_2id(Integer id1, Integer id2) {

        ParameterizedTypeReference<List<ProductCateg3Request>> responseType = new ParameterizedTypeReference<>() {
        };
        List<ProductCateg3Request> data = new ArrayList<>();

        try {
            data = restTemplate.exchange(
                    "http://product-service/category/getCategory3/" + id1 + "/" + id2,
                    HttpMethod.GET, null, responseType).getBody();

        } catch (Exception ex) {
            System.out.println("Unable to get Data :- cat3List_by_cat1_2id");
            System.out.println(ex);
        }
        return data;

    }

    @Override
    public ProductCateg3Request getCategory3ById(int id) {
        return restTemplate.getForObject("http://product-service/category/getCategory3/" + id, ProductCateg3Request.class);
    }
}
