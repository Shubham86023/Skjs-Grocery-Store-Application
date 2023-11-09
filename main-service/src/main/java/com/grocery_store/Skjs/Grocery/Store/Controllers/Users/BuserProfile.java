package com.grocery_store.Skjs.Grocery.Store.Controllers.Users;

import com.grocery_store.Skjs.Grocery.Store.Config.Messages;
import com.grocery_store.Skjs.Grocery.Store.Config.PasswordProtection;
import com.grocery_store.Skjs.Grocery.Store.Dao.BusinessRepo.BusinessDetailsRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.AllUserAddressRepository;
import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.BUserRepository;
import com.grocery_store.Skjs.Grocery.Store.Entities.Business.BusinessDetails;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.AllUserAddress;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.Buser;
import com.grocery_store.Skjs.Grocery.Store.Services.AuthenticationServices.Login_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.CartServices.CartCookies_ops;
import com.grocery_store.Skjs.Grocery.Store.Services.ProductServices.ProductCategory_ops;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@AllArgsConstructor
public class BuserProfile {

    private final ProductCategory_ops productCategoryOps;
    private final CartCookies_ops cartCookiesOps;

    private final Login_ops loginOps;
    private final PasswordProtection crypting;

    private final HttpSession session;

    private final AllUserAddressRepository address_repo;
    private final BUserRepository buser_repo;
    private final BusinessDetailsRepository busiD_repo;

    @RequestMapping("/bEditProfile")
    public String bEditProfile(Model m) {

        if (loginOps.isLoggedIn()) {
            String loginType = loginOps.loginType();
            if (loginType.equals("bwelcome")) {
                Map<String, Object> attributes = new HashMap<>();
                attributes.put("categories1", productCategoryOps.cat2List());
                attributes.put("buser_name", session.getAttribute("buser_name"));
                attributes.put("account_type", "business");
                attributes.put("bis_login", true);
                attributes.put("is_login", false);

                // sending cart qty & amount
                List<Object> qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
                attributes.put("qtyAmount", qtyAmount);

                Integer buserId = (Integer) session.getAttribute("buser_id");
                attributes.put("buserId", buserId);
                // sending buser profile
                Optional<Buser> buser = buser_repo.findById(buserId);
                Buser a = new Buser();
                if (buser.isPresent()) {
                    a = buser.get();
                }
                attributes.put("signup_data", a);

                // sending buser address
                List<AllUserAddress> all_address = address_repo.findByUserIdAndUserType(buserId, "buser");
                if (all_address.size() != 0) {
                    attributes.put("all_address", all_address);
                } else {
                    AllUserAddress row1 = new AllUserAddress();
                    row1.setAddress("");
                    row1.setId(0);

                    List<AllUserAddress> defaultadd = new ArrayList<>();
                    defaultadd.add(row1);
                    attributes.put("all_address", defaultadd);
                }
                m.addAllAttributes(attributes);
                return "login/bEditProfile";
            }
        }
        return "redirect:/homepage";

    }

    @RequestMapping("/bEditingProfile")
    public String bEditingProfile(@ModelAttribute("signup_data") Buser user_sign,
                                  @RequestParam(value = "address", defaultValue = "") List<String> address,
                                  @RequestParam("addressId[]") ArrayList<Integer> addressId,
                                  RedirectAttributes ra) {

        Integer buserId = (Integer) session.getAttribute("buser_id");
        Optional<Buser> user = buser_repo.findById(buserId);
        Buser a = user.get();
        a.setFullName(user_sign.getFullName());
        a.setEmail(user_sign.getEmail());
        a.setMoblieNumber(user_sign.getMoblieNumber());
        a.setDob(user_sign.getDob());
        if (!user_sign.getPassword().isEmpty()) {
            a.setPassword(crypting.encrypt(user_sign.getPassword()));
        }
        Buser updateduser = buser_repo.save(a);

        int i = 0;
        for (i = 0; i < address.size(); i++) {
            AllUserAddress add;
            String correct_add = address.get(i).replace("/", ",");
            if (addressId.isEmpty() || (addressId.get(i) == null) || (addressId.get(i) == 0)) {
                add = new AllUserAddress();
                add.setAddress(correct_add);
                add.setUserType("buser");
                add.setUserId(buserId);
            } else {
                Optional<AllUserAddress> get_address = address_repo.findById(addressId.get(i));
                add = get_address.get();
                add.setAddress(correct_add);
                add.setUserType("buser");
                add.setUserId(buserId);
            }
            address_repo.save(add);
        }

        String message, messageType = "";
        if (updateduser != null) {
            session.setAttribute("buser_name", updateduser.getFullName());
            message = Messages.ACCOUNT_UPDATED;
            messageType = "alert-success";
        } else {
            message = Messages.ERROR_404;
            messageType = "alert-danger";
        }
        ra.addFlashAttribute("message", message);
        ra.addFlashAttribute("messageType", messageType);
        return "redirect:/bEditProfile";
    }

    @RequestMapping("/removeUserAddress")
    @ResponseBody
    public Boolean removeUserAddress(@RequestParam(value = "addressId", defaultValue = "") Integer addressId) {
        address_repo.deleteById(addressId);
        return true;
    }


    @RequestMapping("/updatePersonalInfo")
    public String updatePersonalInfo(Model m) {

        if (loginOps.isLoggedIn()) {
            String loginType = loginOps.loginType();
            if (loginType.equals("bwelcome")) {

                Map<String, Object> attributes = new HashMap<>();
                // sending cart qty & amount
                List<Object> qtyAmount = cartCookiesOps.getCartTotalQtyAndAmount();
                attributes.put("qtyAmount", qtyAmount);
                attributes.put("bis_login", true);
                attributes.put("is_login", false);

                Integer buserId = (Integer) session.getAttribute("buser_id");
                BusinessDetails busi_d = busiD_repo.findByBuserId(buserId);
                if (busi_d == null) {
                    busi_d = new BusinessDetails();
                }

                m.addAttribute("business_details", busi_d);
                // business_details
                attributes.put("categories1", productCategoryOps.cat2List());
                attributes.put("buser_name", session.getAttribute("buser_name"));
                attributes.put("buserId", buserId);
                m.addAllAttributes(attributes);
                return "business/businessDetails";
            }
        }
        return "redirect:/homepage";

    }

    @RequestMapping("/updatingPersonalInfo")
    public String updatingPersonalInfo(
            @ModelAttribute("business_details") BusinessDetails business_details, RedirectAttributes ra) {

        Integer buserId = (Integer) session.getAttribute("buser_id");
        BusinessDetails busi_d = busiD_repo.findByBuserId(buserId);

        if (business_details.getStoreName() != null && !business_details.getStoreName().isEmpty()) {

            if (busi_d == null) {
                busi_d = new BusinessDetails();
                busi_d.setBuserId(buserId);
            }
            busi_d.setStoreName(business_details.getStoreName());
            busi_d.setAddress(business_details.getAddress());
            busi_d.setPincode(business_details.getPincode());
            busi_d.setCity(business_details.getCity());
            busi_d.setState(business_details.getState());
            busi_d.setBankName(business_details.getBankName());
            busi_d.setAccountNo(business_details.getAccountNo());
            busi_d.setIfscCode(business_details.getIfscCode());
            busi_d.setBranchName(business_details.getBranchName());
            BusinessDetails busi_saved = busiD_repo.save(busi_d);
            if (busi_saved != null) {
                ra.addFlashAttribute("message", Messages.BSNSS_DTL_UPDATED);
                ra.addFlashAttribute("messageType", "alert-success");
            } else {
                ra.addFlashAttribute("message", Messages.ERROR_404);
                ra.addFlashAttribute("messageType", "alert-danger");
            }
        }
        return "redirect:/updatePersonalInfo";
    }

}
