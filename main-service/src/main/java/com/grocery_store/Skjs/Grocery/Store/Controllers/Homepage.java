package com.grocery_store.Skjs.Grocery.Store.Controllers;

import com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Login_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.DataForHomepages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@AllArgsConstructor
public class Homepage {

    private final DataForHomepages pageData;
    private final Login_ops loginOps;

    @GetMapping(value = {"/", "/homepage"})
    public String homepage(Model m) {
        if (loginOps.isLoggedIn()) {
            return "redirect:/" + loginOps.loginType();
        } else {
            Map<String, Object> attributes = pageData.provideHomepageData();
            m.addAllAttributes(attributes);
            return "index";
        }
    }

    @RequestMapping("/welcome")
    public String welcome(Model m) {
        String loginType = loginOps.loginType();
        if (loginOps.isLoggedIn() && loginType.equals("welcome")) {
            Map<String, Object> attributes = pageData.provideHomepageData();
            m.addAllAttributes(attributes);
            return "/" + loginType;
        }
        return "redirect:/homepage";
    }

    @RequestMapping("/bwelcome")
    public String bwelcome(Model m) {

        String loginType = loginOps.loginType();
        if (loginOps.isLoggedIn() && loginType.equals("bwelcome")) {
            Map<String, Object> attributes = pageData.provideBusinessDashboardData();
            m.addAllAttributes(attributes);
            return "/" + loginType;
        }
        return "redirect:/homepage";
    }


}
