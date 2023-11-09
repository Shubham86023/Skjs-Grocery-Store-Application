package com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices;

import com.grocery_store.Skjs.Grocery.Store.DTO.authentication.SignUpRequest;

import java.util.Map;

public interface Signup_ops {

    Map<String, Object> signingUp(SignUpRequest signUpRequest, String conf_password);

}
