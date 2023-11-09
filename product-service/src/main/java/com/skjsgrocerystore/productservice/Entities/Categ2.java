package com.skjsgrocerystore.productservice.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_productCateg2")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categ2 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int cat1Id;

    private String cat2Name;

}
