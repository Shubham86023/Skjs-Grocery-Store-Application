package com.skjsgrocerystore.productservice.DTO.Operations;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductIdResponse {

    @NotNull(message = "Business user ID not ne null")
    private Integer buserId;

    @NotNull(message = "Category 1 ID not ne null")
    private Integer cat1Id;

    @NotNull(message = "Category 2 ID not ne null")
    private Integer cat2Id;

    @NotNull(message = "Category 3 ID not ne null")
    private Integer cat3Id;
}
