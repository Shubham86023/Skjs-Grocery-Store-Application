package com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.DTO.ProductMgmt.Points.ProductPointsRequest;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductPoints_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductPointsImpl implements ProductPoints_ops {

    private final RestTemplate restTemplate;

    @Override
    public List<ProductPointsRequest> getProductPoints(int id) {

        List<ProductPointsRequest> points = new ArrayList<>();
        try {
            points = restTemplate.getForObject("http://product-service/points/getPoints/" + id, List.class);
        } catch (Exception ex) {
            System.out.println("No Product Points Got.");
            System.out.println(ex);
            points.add(new ProductPointsRequest());
        }
        return points;
    }


    @Override
    public boolean deletePoint(int pointId) {
        restTemplate.delete("http://product-service/points/deletePoint/" + pointId);
        return true;
    }
}
