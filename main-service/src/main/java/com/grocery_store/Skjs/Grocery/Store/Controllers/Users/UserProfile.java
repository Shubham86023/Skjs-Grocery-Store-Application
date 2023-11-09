package com.grocery_store.Skjs.Grocery.Store.Controllers.Users;

import com.grocery_store.Skjs.Grocery.Store.Config.Messages;
import com.grocery_store.Skjs.Grocery.Store.Config.PasswordProtection;
import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.AllUserAddressRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.UserRepository;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.AllUserAddress;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.User;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductCategory_ops;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserProfile {

    private final ProductCategory_ops productCategoryOps;
    private final CartCookies_ops cartCookiesOps;

    private final UserRepository user_repo;
    private final AllUserAddressRepository address_repo;

    private final PasswordProtection crypting;

    private final HttpSession session;
    private final HttpServletResponse response;


    @RequestMapping("/editProfile")
    public String editProfile(Model m) {

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
        response.setHeader("Pragma", "no-cache"); // http 1.0
        response.setHeader("Expires", "0"); // proxies

        Boolean is_login_check = false;
        is_login_check = (Boolean) session.getAttribute("is_login");
        if (is_login_check != null && is_login_check) {
            // sending cart qty & amount
            List<Object> qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
            m.addAttribute("qtyAmount", qtyAmount);
            // sending profile detail
            Integer userId = (Integer) session.getAttribute("user_id");
            Optional<User> user = user_repo.findById(userId);
            User a;
            if (user.isPresent()) {
                a = user.get();
            } else {
                a = new User();
            }
            m.addAttribute("signup_data", a);
            // sending address
            List<AllUserAddress> all_address = address_repo.findByUserIdAndUserType(userId, "user");
            if (all_address.size() != 0) {
                m.addAttribute("all_address", all_address);
            } else {
                AllUserAddress row1 = new AllUserAddress();
                row1.setAddress("");
                row1.setId(0);

                List<AllUserAddress> defaultadd = new ArrayList<>();
                defaultadd.add(row1);
                m.addAttribute("all_address", defaultadd);
            }

            m.addAttribute("categories1", productCategoryOps.cat2List());
            m.addAttribute("user_name", session.getAttribute("user_name"));
            m.addAttribute("userId", userId);
            m.addAttribute("is_login", true);
            m.addAttribute("bis_login", false);
            return "login/editProfile";
        } else {
            return "redirect:/homepage";
        }
    }

    @RequestMapping("/editingProfile")
    public String editingProfile(Model m, @ModelAttribute("signup_data") User user_sign,
                                 @RequestParam(value = "address", defaultValue = "") List<String> address,
                                 @RequestParam("addressId[]") ArrayList<Integer> addressId, RedirectAttributes ra) {

        Integer userId = (Integer) session.getAttribute("user_id");
        Optional<User> user = user_repo.findById(userId);
        User a = user.get();
        a.setFullName(user_sign.getFullName());
        a.setEmail(user_sign.getEmail());
        a.setMoblieNumber(user_sign.getMoblieNumber());
        a.setDob(user_sign.getDob());
        if (!user_sign.getPassword().isEmpty()) {
            a.setPassword(crypting.encrypt(user_sign.getPassword()));
        }
        User updateduser = user_repo.save(a);

        int i = 0;
        for (i = 0; i < address.size(); i++) {
            AllUserAddress add;
            String correct_add = address.get(i).replace("/", ",");
            if (addressId.isEmpty() || (addressId.get(i) == null) || (addressId.get(i) == 0)) {
                add = new AllUserAddress();
                add.setAddress(correct_add);
                add.setUserType("user");
                add.setUserId(userId);
            } else {
                Optional<AllUserAddress> get_address = address_repo.findById(addressId.get(i));
                add = get_address.get();
                add.setAddress(correct_add);
                add.setUserType("user");
                add.setUserId(userId);
            }
            address_repo.save(add);
        }

        String message, messageType = "";
        if (updateduser != null) {
            session.setAttribute("user_name", updateduser.getFullName());
            message = Messages.ACCOUNT_UPDATED;
            messageType = "alert-success";

        } else {
            message = Messages.ERROR_404;
            messageType = "alert-danger";
        }
        ra.addFlashAttribute("message", message);
        ra.addFlashAttribute("messageType", messageType);
        return "redirect:/editProfile";
    }
}
