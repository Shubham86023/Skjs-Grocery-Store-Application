package com.grocery_store.Skjs.Grocery.Store.Entities.Orders;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_cart")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String userType;

    private int userid;

    private int productid;

    private Integer qty;

}
