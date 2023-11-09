package com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.Config.Messages;
import com.grocery_store.Skjs.Grocery.Store.Config.PasswordProtection;
import com.grocery_store.Skjs.Grocery.Store.DTO.authentication.SignUpRequest;
import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.BUserRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.UserRepository;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.Buser;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.User;
import com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Signup_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class SignUpImpl implements Signup_ops {

    private final UserRepository user_repo;
    private final BUserRepository buser_repo;
    private final PasswordProtection crypting;

    @Override
    public Map<String, Object> signingUp(SignUpRequest signUpRequest, String conf_password) {

        int status = 0;
        String message = "";

        Map<String, Object> attributes = new HashMap<>();

        if (conf_password.equals(signUpRequest.getPassword())) {

            if (signUpRequest.getAccount_type().equals("business")) {
                Buser u1 = buser_repo.findByEmailOrMoblieNumber(signUpRequest.getEmail(),
                        signUpRequest.getMoblieNumber());
                if (u1 == null) {

                    Buser buser = Buser.builder()
                            .fullName(signUpRequest.getFullName())
                            .email(signUpRequest.getEmail())
                            .password(crypting.encrypt(signUpRequest.getPassword()))
                            .moblieNumber(signUpRequest.getMoblieNumber())
                            .dob(signUpRequest.getDob())
                            .build();

                    buser_repo.save(buser);
                    status = 1;
                } else {
                    message = Messages.UNQ_EMAIL_MOBILE;
                }
            } else {
                User u1 = user_repo.findByEmailOrMoblieNumber(signUpRequest.getEmail(),
                        signUpRequest.getMoblieNumber());
                if (u1 == null) {
                    User user = User.builder()
                            .fullName(signUpRequest.getFullName())
                            .email(signUpRequest.getEmail())
                            .password(crypting.encrypt(signUpRequest.getPassword()))
                            .moblieNumber(signUpRequest.getMoblieNumber())
                            .dob(signUpRequest.getDob())
                            .build();

                    user_repo.save(user);
                    status = 1;
                } else {
                    message = Messages.UNQ_EMAIL_MOBILE;
                }
            }
            if (status == 1) {
                signUpRequest = new SignUpRequest();
                message = Messages.ACCOUNT_CREATED;
            }
        } else {
            message = Messages.BOTH_PASS_NOT_MATCH;
        }

        attributes.put("message", message);
        attributes.put("signup_data", signUpRequest);
        attributes.put("status", status);

        return attributes;
    }
}
