package com.skjsgrocerystore.productservice.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_productCateg3")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categ3 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int cat1Id;

    private int cat2Id;

    private String cat3Name;

}
