package com.skjsgrocerystore.productservice.DTO.Operations;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStep1Response {

    private int id;

    @Min(value = 1, message = "Product ID should not be null")
    private int productId;

    @Min(value = 1, message = "Business user ID should not be null")
    private int buserId;

    @Min(value = 1, message = "Category 1 ID should not be null")
    private int cat1Id;

    @Min(value = 1, message = "Category 2 ID should not be null")
    private int cat2Id;

    @Min(value = 1, message = "Category 3 ID should not be null")
    private int cat3Id;

    @NotBlank(message = "Product Name should not be null")
    private String productName;

    private String brandName;

    @Min(0)
    @Max(1)
    private int status;

    @NotBlank(message = "DateTime created should not be null")
    private String dateTimeCreated;

    @NotBlank(message = "DateTime modified should not be null")
    private String dateTimeModified;

}
