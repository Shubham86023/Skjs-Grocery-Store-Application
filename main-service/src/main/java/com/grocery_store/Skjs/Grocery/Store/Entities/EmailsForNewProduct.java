package com.grocery_store.Skjs.Grocery.Store.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_emailsForNewProduct")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailsForNewProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String email;

}
