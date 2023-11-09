package com.grocery_store.Skjs.Grocery.Store.Config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordProtection {

    public String encrypt(String raw_password) {
        BCryptPasswordEncoder crypter = new BCryptPasswordEncoder();
        return crypter.encode(raw_password);
    }

    public boolean checker(String logging_pass, String encoded_correct_pass) {
        BCryptPasswordEncoder crypter = new BCryptPasswordEncoder();
        return crypter.matches(logging_pass, encoded_correct_pass);
    }
}
