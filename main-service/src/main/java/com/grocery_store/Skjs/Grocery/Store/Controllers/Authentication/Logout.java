package com.grocery_store.Skjs.Grocery.Store.Controllers.Authentication;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Logout {

    private final HttpSession session;

    public Logout(HttpSession session) {
        this.session = session;
    }

    @RequestMapping("/logout")
    public String logout() {
        session.setAttribute("is_login", false);
        return "redirect:/homepage";
    }

    @RequestMapping("/blogout")
    public String blogout() {
        session.setAttribute("bis_login", false);
        // session.invalidate();
        return "redirect:/homepage";
    }
}
