package com.skjsgrocerystore.productservice.DTO.Images;


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
public class ImagesResponse {

    private int id;

    @NotNull(message = "Product ID is not be Null or Empty.")
    private Integer proId;

    @NotBlank(message = "Image Name can not be Null or Empty.")
    private String mainImage;

    private String image2;

    private String image3;

    private String image4;

    private String image5;

}
