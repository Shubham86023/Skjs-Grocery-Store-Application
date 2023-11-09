package com.skjsgrocerystore.productservice.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_productReviews")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int userId;

    private int productId;

    private int rating;

    private String date;

    private String reviewerType;

    private String reviewerMessage;

}
