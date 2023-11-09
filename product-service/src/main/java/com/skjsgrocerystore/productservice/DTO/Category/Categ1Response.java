package com.skjsgrocerystore.productservice.DTO.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categ1Response {

    private int id;
    private String cat1Name;

}
