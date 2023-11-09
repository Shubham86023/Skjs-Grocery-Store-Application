package com.grocery_store.Skjs.Grocery.Store.Entities.Users;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String fullName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String moblieNumber;

    private String dob;


}
