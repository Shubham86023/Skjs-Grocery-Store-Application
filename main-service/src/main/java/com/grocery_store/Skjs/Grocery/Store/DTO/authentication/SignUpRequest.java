package com.grocery_store.Skjs.Grocery.Store.DTO.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    private int id;
    private String fullName;
    private String email;
    private String password;
    private String moblieNumber;
    private String dob;
    private String account_type;
}
