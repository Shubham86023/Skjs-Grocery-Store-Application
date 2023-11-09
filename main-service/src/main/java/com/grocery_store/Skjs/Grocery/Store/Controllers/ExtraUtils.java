package com.grocery_store.Skjs.Grocery.Store.Controllers;

import com.grocery_store.Skjs.Grocery.Store.Dao.EmailsForNewProduct_Repository;
import com.grocery_store.Skjs.Grocery.Store.Entities.EmailsForNewProduct;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExtraUtils {

    private final EmailsForNewProduct_Repository efp_repo;

    public ExtraUtils(EmailsForNewProduct_Repository efp_repo) {
        this.efp_repo = efp_repo;
    }

    @RequestMapping("/savingEmailsForNewProduct")
    @ResponseBody
    public Integer savingEmailsForNewProduct(
            @RequestParam(value = "email", defaultValue = "") String email) {

        try {
            if (email == null) {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            return 0;
        }

        EmailsForNewProduct em = efp_repo.findByEmail(email);
        if (em == null) {
            EmailsForNewProduct data = new EmailsForNewProduct();
            data.setEmail(email);
            efp_repo.save(data);
            return 1;
        } else {
            return 0;
        }
    }
}
