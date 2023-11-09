package com.skjsgrocerystore.productservice.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_productCateg1")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categ1 {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String cat1Name;

}
