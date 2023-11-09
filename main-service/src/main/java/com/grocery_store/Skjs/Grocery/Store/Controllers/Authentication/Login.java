package com.grocery_store.Skjs.Grocery.Store.Controllers.Authentication;

import com.grocery_store.Skjs.Grocery.Store.DTO.authentication.LoginDetails;
import com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Login_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@AllArgsConstructor
public class Login {

    private final Login_ops loginCheckOps;

    @RequestMapping("/login")
    public String login_page(Model m) {
        m.addAttribute("business_check", "");
        return "login/login";
    }

    @RequestMapping("/loginBusiness")
    public String loginBusiness(Model m) {
        m.addAttribute("business_check", "on");
        return "login/login";
    }

    @PostMapping("/loggingIn")
    public String loggingIn(Model m, @ModelAttribute LoginDetails loginDetails) {
        Map<String, Object> attributes = loginCheckOps.authenticating(loginDetails);
        m.addAllAttributes(attributes);
        return (String) attributes.get("nextPage");
    }


    @RequestMapping("/forgotPassword")
    public String forgotPassword(Model m) {
        m.addAttribute("business_check", "");
        return "login/forgotPassword";

    }


    @RequestMapping("/bForgotPassword")
    public String bForgotPassword(Model m) {
        m.addAttribute("business_check", "on");
        return "login/forgotPassword";

    }

    @RequestMapping("/forgottingPassword")
    public String forgottingPassword(Model m, @ModelAttribute LoginDetails loginDetails,
                                     @RequestParam(value = "conf_password") String conf_password) {
        Map<String, Object> attributes = loginCheckOps.updatePassword(loginDetails, conf_password);
        m.addAllAttributes(attributes);
        return "login/forgotPassword";

    }


}
