package com.skjsgrocerystore.productservice.DAO;

import com.skjsgrocerystore.productservice.Entities.Categ2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Categ2Repository extends JpaRepository<Categ2, Integer> {

    List<Categ2> findAllByCat1Id(int id);

}
