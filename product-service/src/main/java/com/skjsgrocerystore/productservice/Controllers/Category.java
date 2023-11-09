package com.skjsgrocerystore.productservice.Controllers;

import com.skjsgrocerystore.productservice.DTO.Category.Categ1Response;
import com.skjsgrocerystore.productservice.DTO.Category.Categ2Response;
import com.skjsgrocerystore.productservice.DTO.Category.Categ3Response;
import com.skjsgrocerystore.productservice.Exceptions.ResourceNotFoundException;
import com.skjsgrocerystore.productservice.Services.Category_ops;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class Category {

    private final Category_ops productCategoryOps;

    @GetMapping("/getCategory1")
    public ResponseEntity<List<Categ1Response>> getAllProductCategory1() {
        List<Categ1Response> list = productCategoryOps.cat1List();

        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } else {
            throw new ResourceNotFoundException("No Category 1 Available.");
        }
    }

    @GetMapping("/getCategory2")
    public ResponseEntity<List<Categ2Response>> getAllProductCategory2() {
        List<Categ2Response> list = productCategoryOps.cat2List();

        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } else {
            throw new ResourceNotFoundException("No Category 2 Available.");
        }
    }


    @GetMapping("/getCategory2/{id1}")
    public ResponseEntity<List<Categ2Response>> getAllProductCategory2Bycat1Id(@PathVariable int id1) {
        List<Categ2Response> list = productCategoryOps.cat2List_by_cat1id(id1);

        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/getCategory3/{id1}/{id2}")
    public ResponseEntity<List<Categ3Response>> getAllProductCategory3Bycat1_2Id(
            @PathVariable int id1, @PathVariable int id2) {
        List<Categ3Response> list = productCategoryOps.cat3List_by_cat1_2id(id1, id2);

        if (!list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping("/getCategory3/{id3}")
    public ResponseEntity<Categ3Response> getProductCategory3ById(
            @PathVariable int id3) {
        Categ3Response cat3 = productCategoryOps.getCategory3ById(id3);

        if (cat3 != null) {
            return ResponseEntity.status(HttpStatus.OK).body(cat3);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
