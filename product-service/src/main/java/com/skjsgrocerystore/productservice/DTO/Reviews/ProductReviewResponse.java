package com.skjsgrocerystore.productservice.DTO.Reviews;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReviewResponse {

    private int id;

    @NotNull(message = "User ID should not be null.")
    private Integer userId;

    @NotNull(message = "Product ID should not be null.")
    private Integer productId;

    @Nullable
    private Integer checkbox_result;

    @Nullable
    private String reviewerMessage;

    @NotBlank(message = "Reviewer Type not be Blank.")
    private String reviewerType;

    private String date;
}
