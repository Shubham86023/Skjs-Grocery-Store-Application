package com.skjsgrocerystore.productservice.Services;


import com.skjsgrocerystore.productservice.DTO.Point.PointsResponse;

import java.util.List;

public interface Points_ops {

    List<PointsResponse> getPoints(int id);

    boolean saveProductPoints(List<String> points, int id, List<Integer> idsList);

    boolean deletePoint(int pointId);

}
