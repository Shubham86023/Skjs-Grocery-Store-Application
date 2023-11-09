package com.skjsgrocerystore.productservice.Services.Implementations;


import com.skjsgrocerystore.productservice.DAO.PointsRepository;
import com.skjsgrocerystore.productservice.DTO.Point.PointsResponse;
import com.skjsgrocerystore.productservice.Entities.Points;
import com.skjsgrocerystore.productservice.Services.Points_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PointsImpl implements Points_ops {

    private final PointsRepository pointsRepo;

    @Override
    public List<PointsResponse> getPoints(int id) {

        List<Points> data = pointsRepo.findByProId(id);

        List<PointsResponse> points = new ArrayList<>();
        if (!data.isEmpty()) {
            for (Points p : data) {
                PointsResponse res = PointsResponse.builder()
                        .id(p.getId())
                        .proId(p.getProId())
                        .point(p.getPoint()).build();
                points.add(res);
            }
        } else {
            points.add(new PointsResponse());
        }
        return points;
    }

    @Override
    public boolean saveProductPoints(List<String> points, int id, List<Integer> idsList) {

        for (int i = 0; i < points.size(); i++) {
            Points output = null;
            String correct_point = points.get(i).replace("/", ",");
            if (idsList.isEmpty() || (idsList.get(i) == null) || (idsList.get(i) == 0)) {
                output = Points.builder()
                        .point(correct_point)
                        .proId(id)
                        .build();
            } else {
                Optional<Points> get_point = pointsRepo.findById(idsList.get(i));
                if (get_point.isPresent()) {
                    output = get_point.get();
                    output.setPoint(correct_point);
                }

            }
            if (output != null) {
                pointsRepo.save(output);
            }
        }
        return true;
    }

    @Override
    public boolean deletePoint(int pointId) {
        pointsRepo.deleteById(pointId);
        return true;
    }
}
