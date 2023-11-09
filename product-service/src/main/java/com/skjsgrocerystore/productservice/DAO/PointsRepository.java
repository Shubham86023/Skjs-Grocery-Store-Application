package com.skjsgrocerystore.productservice.DAO;

import com.skjsgrocerystore.productservice.Entities.Points;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointsRepository extends JpaRepository<Points, Integer> {

    List<Points> findByProId(int id);

}
