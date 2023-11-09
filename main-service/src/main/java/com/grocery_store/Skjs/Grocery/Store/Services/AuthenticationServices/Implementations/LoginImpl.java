package com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Implementations;

import com.grocery_store.Skjs.Grocery.Store.Config.Messages;
import com.grocery_store.Skjs.Grocery.Store.Config.PasswordProtection;
import com.grocery_store.Skjs.Grocery.Store.DTO.authentication.LoginDetails;
import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.BUserRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.UserRepository;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.Buser;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.User;
import com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Login_ops;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LoginImpl implements Login_ops {

    private final HttpSession session;
    private final HttpServletResponse response;

    private final UserRepository user_repo;
    private final BUserRepository buser_repo;

    private final PasswordProtection crypting;

    public String loginType() {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_user_login = (Boolean) session.getAttribute("is_login");
        Boolean is_business_user_login = (Boolean) session.getAttribute("bis_login");

        is_user_login = is_user_login != null && is_user_login;
        is_business_user_login = is_business_user_login != null && is_business_user_login;

        if (is_user_login) {
            return "welcome";
        } else if (is_business_user_login) {
            return "bwelcome";
        }
        return "homepage";
    }

    public boolean isLoggedIn() {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_user_login = (Boolean) session.getAttribute("is_login");
        Boolean is_business_user_login = (Boolean) session.getAttribute("bis_login");

        is_user_login = is_user_login != null && is_user_login;
        is_business_user_login = is_business_user_login != null && is_business_user_login;

        return is_user_login || is_business_user_login;
    }


    public Map<String, Object> authenticating(LoginDetails loginDetails) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        loginDetails.setUserType(loginDetails.getBusiness_check() == null ? "USER" : "BUSER");

        Map<String, Object> attributes = new HashMap<>();

        if (Objects.equals(loginDetails.getUserType(), "BUSER")) {
            Buser u1 = buser_repo.findByEmailOrMoblieNumber(loginDetails.getUsername()
                    , loginDetails.getUsername());

            if (u1 != null && crypting.checker(loginDetails.getPassword(), u1.getPassword())) {
                session.setAttribute("buser_name", u1.getFullName());
                session.setAttribute("buser_id", u1.getId());
                session.setAttribute("bis_login", true);
                attributes.put("nextPage", "redirect:/bwelcome");
                return attributes;
            }

        } else {
            User u1 = user_repo.findByEmailOrMoblieNumber(loginDetails.getUsername()
                    , loginDetails.getUsername());

            if (u1 != null && crypting.checker(loginDetails.getPassword(), u1.getPassword())) {
                session.setAttribute("user_name", u1.getFullName());
                session.setAttribute("user_id", u1.getId());
                session.setAttribute("is_login", true);

                attributes.put("nextPage", "redirect:/welcome");
                return attributes;
            }
        }

        attributes.put("business_check", loginDetails.getBusiness_check());
        attributes.put("emailORnumber", loginDetails.getUsername());
        attributes.put("message", Messages.WRONG_CREDENTIALS);
        attributes.put("status", 0);
        attributes.put("nextPage", "login/login");
        return attributes;
    }

    @Override
    public Map<String, Object> updatePassword(LoginDetails loginDetails, String conf_password) {

        int status = 0;
        String message = "";

        if (loginDetails.getPassword().equals(conf_password)) {

            loginDetails.setUserType(loginDetails.getBusiness_check() == null ? "USER" : "BUSER");
            boolean passStatus = false;
            if (Objects.equals(loginDetails.getUserType(), "BUSER")) {
                Buser u1 = buser_repo.findByEmailOrMoblieNumber(loginDetails.getUsername()
                        , loginDetails.getUsername());
                if ((u1 != null)) {
                    u1.setPassword(crypting.encrypt(loginDetails.getPassword()));
                    buser_repo.save(u1);
                    passStatus = true;
                }
            } else {
                User u1 = user_repo.findByEmailOrMoblieNumber(loginDetails.getUsername()
                        , loginDetails.getUsername());
                if ((u1 != null)) {
                    u1.setPassword(crypting.encrypt(loginDetails.getPassword()));
                    user_repo.save(u1);
                    passStatus = true;
                }
            }

            if (passStatus) {
                message = Messages.PASSWORD_UPDATED;
                status = 1;
            } else {
                message = Messages.NO_ACCOUNT_AVL;
            }
        } else {
            message = Messages.BOTH_PASS_NOT_MATCH;

        }

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("message", message);
        attributes.put("emailORnumber", loginDetails.getUsername());
        attributes.put("status", status);
        attributes.put("business_check", loginDetails.getBusiness_check());

        return attributes;
    }

}
