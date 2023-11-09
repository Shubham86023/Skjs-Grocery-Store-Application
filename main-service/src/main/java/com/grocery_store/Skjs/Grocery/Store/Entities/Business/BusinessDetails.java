package com.grocery_store.Skjs.Grocery.Store.Entities.Business;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_business_details")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int buserId;

    private String storeName;

    private String address;

    private String pincode;

    private String city;

    private String state;

    private String bankName;

    private String accountNo;

    private String ifscCode;

    private String branchName;

}
