package com.grocery_store.Skjs.Grocery.Store.DTO.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDetails {

    private String business_check;
    private String username;
    private String password;
    private String userType;

}

