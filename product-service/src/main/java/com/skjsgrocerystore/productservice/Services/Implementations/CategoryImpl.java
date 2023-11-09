package com.skjsgrocerystore.productservice.Services.Implementations;

import com.skjsgrocerystore.productservice.DAO.Categ1Repository;
import com.skjsgrocerystore.productservice.DAO.Categ2Repository;
import com.skjsgrocerystore.productservice.DAO.Categ3Repository;
import com.skjsgrocerystore.productservice.DTO.Category.Categ1Response;
import com.skjsgrocerystore.productservice.DTO.Category.Categ2Response;
import com.skjsgrocerystore.productservice.DTO.Category.Categ3Response;
import com.skjsgrocerystore.productservice.Entities.Categ1;
import com.skjsgrocerystore.productservice.Entities.Categ2;
import com.skjsgrocerystore.productservice.Entities.Categ3;
import com.skjsgrocerystore.productservice.Services.Category_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryImpl implements Category_ops {

    private final Categ1Repository categ1Repository;
    private final Categ2Repository categ2Repository;
    private final Categ3Repository categ3Repository;

    @Override
    public List<Categ1Response> cat1List() {

        List<Categ1Response> data = new ArrayList<>();

        for (Categ1 c : categ1Repository.findAll()) {
            Categ1Response response = Categ1Response.builder()
                    .id(c.getId())
                    .cat1Name(c.getCat1Name()).build();

            data.add(response);
        }
        return data;

    }

    @Override
    public List<Categ2Response> cat2List() {

        List<Categ2Response> data = new ArrayList<>();

        for (Categ2 c : categ2Repository.findAll()) {
            Categ2Response response = Categ2Response.builder()
                    .id(c.getId())
                    .cat1Id(c.getCat1Id())
                    .cat2Name(c.getCat2Name()).build();

            data.add(response);
        }

        return data;
    }

    @Override
    public List<Categ2Response> cat2List_by_cat1id(int id) {

        List<Categ2Response> data = new ArrayList<>();

        for (Categ2 c : categ2Repository.findAllByCat1Id(id)) {
            Categ2Response response = Categ2Response.builder()
                    .id(c.getId())
                    .cat1Id(c.getCat1Id())
                    .cat2Name(c.getCat2Name()).build();

            data.add(response);
        }

        return data;


    }

    @Override
    public List<Categ3Response> cat3List_by_cat1_2id(Integer id1, Integer id2) {
        List<Categ3Response> data = new ArrayList<>();
        for (Categ3 c : categ3Repository.findAllByCat1IdAndCat2Id(id1, id2)) {
            Categ3Response response = Categ3Response.builder()
                    .id(c.getId())
                    .cat1Id(c.getCat1Id())
                    .cat2Id(c.getCat2Id())
                    .cat3Name(c.getCat3Name()).build();

            data.add(response);
        }
        return data;
    }


    @Override
    public Categ3Response getCategory3ById(int id) {
        Categ3 c = categ3Repository.findCat3NameById(id);
        return Categ3Response.builder()
                .id(c.getId())
                .cat1Id(c.getCat1Id())
                .cat2Id(c.getCat2Id())
                .cat3Name(c.getCat3Name()).build();
    }

}
