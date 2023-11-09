package com.skjsgrocerystore.productservice.DAO;

import com.skjsgrocerystore.productservice.Entities.Images;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Integer> {

    Images findByProId(int id);

}
