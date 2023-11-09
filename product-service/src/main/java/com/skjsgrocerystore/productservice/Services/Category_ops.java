package com.skjsgrocerystore.productservice.Services;


import com.skjsgrocerystore.productservice.DTO.Category.Categ1Response;
import com.skjsgrocerystore.productservice.DTO.Category.Categ2Response;
import com.skjsgrocerystore.productservice.DTO.Category.Categ3Response;

import java.util.List;

public interface Category_ops {

    List<Categ1Response> cat1List();

    List<Categ2Response> cat2List();

    List<Categ2Response> cat2List_by_cat1id(int id);

    List<Categ3Response> cat3List_by_cat1_2id(Integer id1, Integer id2);

    Categ3Response getCategory3ById(int id);
}
