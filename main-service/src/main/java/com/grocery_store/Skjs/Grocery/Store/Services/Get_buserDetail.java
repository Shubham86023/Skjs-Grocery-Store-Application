package com.grocery_store.Skjs.Grocery.Store.Services;

import com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo.BUserRepository;
import com.grocery_store.Skjs.Grocery.Store.Entities.Users.Buser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class Get_buserDetail {

    private BUserRepository buser;

    public String getBuserNameById(int id) {
        Buser bu = buser.findById(id);
        return bu.getFullName();
    }
}
