package com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices;

import com.grocery_store.Skjs.Grocery.Store.DTO.authentication.LoginDetails;

import java.util.Map;

public interface Login_ops {

    String loginType();

    boolean isLoggedIn();

    Map<String, Object> authenticating(LoginDetails loginDetails);

    Map<String, Object> updatePassword(LoginDetails loginDetails, String conf_password);

}
