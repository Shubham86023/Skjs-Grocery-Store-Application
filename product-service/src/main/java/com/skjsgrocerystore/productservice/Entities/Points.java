package com.skjsgrocerystore.productservice.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_bPoints")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Points {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int proId;
    private String point;

}
