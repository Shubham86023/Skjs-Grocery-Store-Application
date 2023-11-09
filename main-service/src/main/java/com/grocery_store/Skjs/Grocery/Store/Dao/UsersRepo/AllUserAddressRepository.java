package com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo;

import com.grocery_store.Skjs.Grocery.Store.Entities.Users.AllUserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllUserAddressRepository extends JpaRepository<AllUserAddress, Integer> {

    List<AllUserAddress> findByUserIdAndUserType(Integer userId, String string);

}
