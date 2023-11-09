package com.grocery_store.Skjs.Grocery.Store.Entities.Orders;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_order_items")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int orderId;

    private int productId;

    private int qty;

    private Double price;

    private int sellerPin;

    private int sellerDeliveryStatus;

    private String sellerDeliveryDate;

}
