package com.skjsgrocerystore.productservice.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_images")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int proId;

    private String mainImage;

    private String image2;

    private String image3;

    private String image4;

    private String image5;

}
