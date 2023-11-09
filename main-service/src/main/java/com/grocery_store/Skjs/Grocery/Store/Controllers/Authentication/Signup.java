package com.grocery_store.Skjs.Grocery.Store.Controllers.Authentication;

import com.grocery_store.Skjs.Grocery.Store.DTO.authentication.SignUpRequest;
import com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Signup_ops;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@AllArgsConstructor
public class Signup {

    private final Signup_ops signupOps;

    @RequestMapping("/signupCustomer")
    public String signup_page_customer(Model m) {
        m.addAttribute("status", "");

        SignUpRequest request = new SignUpRequest();
        request.setAccount_type("customer");
        m.addAttribute("signup_data", request);
        return "login/signup";
    }

    @RequestMapping("/signupBusiness")
    public String signup_page_business(Model m) {
        m.addAttribute("status", "");
        SignUpRequest request = new SignUpRequest();
        request.setAccount_type("business");
        m.addAttribute("signup_data", request);
        return "login/signup";
    }

    @RequestMapping("/signup")
    public String signup(Model m, @ModelAttribute("signup_data") SignUpRequest signUpRequest,
                         @RequestParam(value = "conf_password", defaultValue = "") String conf_password) {

        Map<String, Object> attributes = signupOps.signingUp(signUpRequest, conf_password);
        m.addAllAttributes(attributes);
        return "login/signup";
    }

}
