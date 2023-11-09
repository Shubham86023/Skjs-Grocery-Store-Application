package com.grocery_store.Skjs.Grocery.Store.Entities.Orders;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_orders")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int userId;

    private String userType;

    private int addressId;

    private String orderDate;

}
