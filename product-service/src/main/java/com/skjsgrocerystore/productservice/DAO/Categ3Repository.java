package com.skjsgrocerystore.productservice.DAO;

import com.skjsgrocerystore.productservice.Entities.Categ3;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Categ3Repository extends JpaRepository<Categ3, Integer> {

    List<Categ3> findAllByCat1IdAndCat2Id(int id1, int id2);

    Categ3 findCat3NameById(int id);
}
