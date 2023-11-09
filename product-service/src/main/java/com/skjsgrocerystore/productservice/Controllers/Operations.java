package com.skjsgrocerystore.productservice.Controllers;

import com.skjsgrocerystore.productservice.DTO.Operations.*;
import com.skjsgrocerystore.productservice.Exceptions.ResourceNotFoundException;
import com.skjsgrocerystore.productservice.Services.Operations_ops;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/operations")
public class Operations {

    private final Operations_ops operationsOps;

    @GetMapping("/getProduct/{id}")
    public ResponseEntity<ProductDetailResponse> getProduct(@PathVariable int id) {

        ProductDetailResponse product = operationsOps.getProduct(id);

        if (product != null) {
            return ResponseEntity.status(HttpStatus.OK).body(product);
        } else {
            throw new ResourceNotFoundException("No Product found for ID : " + id);
        }
    }

    @PostMapping("/saveProductStep1")
    public ResponseEntity<Integer> saveProductStep1(@RequestBody @Valid ProductStep1Response response) {
        Integer id = operationsOps.saveProductStep1(response);

        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


    @PutMapping("/updateProductStep1/{id}")
    public ResponseEntity<?> updateProductStep1(@PathVariable int id,
                                                @RequestBody ProductStep1Response response) {
        response.setId(id);
        id = operationsOps.updateProductStep1(response);

        if (id != 0) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/updateProductStep2/{id}")
    public ResponseEntity<?> updateProductStep2(@PathVariable int id,
                                                @RequestBody ProductStep2Response response) {
        response.setId(id);
        id = operationsOps.updateProductStep2(response);

        if (id != 0) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateProductStep4/{id}")
    public ResponseEntity<?> updateProductStep4(@PathVariable int id,
                                                @RequestBody ProductStep4Response response) {
        response.setId(id);
        id = operationsOps.updateProductStep4(response);

        if (id != 0) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PutMapping("/updateProduct/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody ProductDetailResponse response) {

        operationsOps.updateProduct(id, response);
        return ResponseEntity.ok("Product Updated successfully");

    }


    @PostMapping("/getLatestProductId")
    public ResponseEntity<Integer> getLatestProductId(@RequestBody @Valid ProductIdResponse response) {

        Integer id = operationsOps.getLatestProductId(response);

        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/getProductsByCategory3Id/{cat3id}/{minLimit}/{maxLimit}")
    public ResponseEntity<List<Object[]>> getProductsByCategory3Id(@PathVariable int cat3id
            , @PathVariable int minLimit, @PathVariable int maxLimit) {

        List<Object[]> pro = operationsOps.getProductsByCategory3Id(cat3id, minLimit, maxLimit);
        if (pro != null) {
            return new ResponseEntity<>(pro, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PostMapping("/getProductsBySearch")
    public ResponseEntity<List<Object[]>> getProductsBySearch(@RequestBody @Valid ProductSearchResponse response) {
        List<Object[]> pro = operationsOps.getProductsBySearch(response);

        if (pro != null) {
            return new ResponseEntity<>(pro, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Product Available.");
        }
    }


    @GetMapping("/getProductsByCategory2Id/{cat2Id}/{limit}")
    public ResponseEntity<List<Object[]>> getProductsByCategory2Id(@PathVariable int cat2Id
            , @PathVariable int limit) {

        List<Object[]> pro = operationsOps.getProductsByCategory2Id(cat2Id, limit);

        if (pro != null) {
            return new ResponseEntity<>(pro, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Product Available.");
        }
    }


    @GetMapping("/getUniqueProductsByCategory3Id")
    public ResponseEntity<List<Object[]>> getUniqueProductsByCategory3Id() {

        List<Object[]> pro = operationsOps.getUniqueProductsByCategory3Id();

        if (pro != null) {
            return new ResponseEntity<>(pro, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Product Available.");
        }
    }

    @GetMapping("/getProductDetailWithImage/{id}")
    public ResponseEntity<List<Object[]>> getProductDetailWithImage(@PathVariable int id) {

        List<Object[]> pro = operationsOps.getProductDetailWithImage(id);

        if (pro != null) {
            return new ResponseEntity<>(pro, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Product Available.");
        }
    }

    @GetMapping("/gettingProductsData/{id}")
    public ResponseEntity<List<Object[]>> gettingProductsData(@PathVariable int id) {

        List<Object[]> pro = operationsOps.gettingProductsData(id);

        if (pro != null) {
            return new ResponseEntity<>(pro, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Product Available.");
        }
    }

//Homepage

    @GetMapping("/gettingProductBycat2id/{cat2Id}/{limit}")
    public ResponseEntity<List<Object[]>> gettingProductBycat2id(@PathVariable int cat2Id, @PathVariable int limit) {

        List<Object[]> pro = operationsOps.gettingProductBycat2id(cat2Id, limit);

        if (pro != null) {
            return new ResponseEntity<>(pro, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Product Available.");
        }
    }


    @GetMapping("/gettingProductByTwocat2Ids/{cat2Id1}/{cat2Id2}/{limit}")
    public ResponseEntity<List<Object[]>> gettingProductByTwocat2Ids(
            @PathVariable int cat2Id1, @PathVariable int cat2Id2, @PathVariable int limit) {

        List<Object[]> pro = operationsOps.gettingProductByTwocat2Ids(cat2Id1, cat2Id2, limit);

        if (pro != null) {
            return new ResponseEntity<>(pro, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Product Available.");
        }
    }


    @GetMapping("/top5HighDiscountProducts")
    public ResponseEntity<List<Object[]>> top5HighDiscountProducts() {

        List<Object[]> pro = operationsOps.top5HighDiscountProducts();

        if (pro != null) {
            return new ResponseEntity<>(pro, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("No Product Available.");
        }
    }
}
