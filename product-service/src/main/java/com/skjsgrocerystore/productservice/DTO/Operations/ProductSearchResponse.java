package com.skjsgrocerystore.productservice.DTO.Operations;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchResponse {


    private int isSearch;

    private String srchWithKywrd;

    private Integer srchWithCtgry2;

    @Min(0)
    private int minLimit;

    @Min(0)
    private int maxLimit;

}

