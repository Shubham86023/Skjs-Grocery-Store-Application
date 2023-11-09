package com.skjsgrocerystore.productservice.DTO.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categ2Response {

    private int id;
    private int cat1Id;
    private String cat2Name;

}
