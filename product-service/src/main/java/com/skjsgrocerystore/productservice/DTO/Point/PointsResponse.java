package com.skjsgrocerystore.productservice.DTO.Point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PointsResponse {

    private int id;
    private int proId;
    private String point;

}
