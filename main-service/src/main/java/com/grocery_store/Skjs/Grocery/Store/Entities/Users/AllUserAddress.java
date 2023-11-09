package com.grocery_store.Skjs.Grocery.Store.Entities.Users;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_allUserAddress")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllUserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int userId;

    private String userType;

    private String address;

}
