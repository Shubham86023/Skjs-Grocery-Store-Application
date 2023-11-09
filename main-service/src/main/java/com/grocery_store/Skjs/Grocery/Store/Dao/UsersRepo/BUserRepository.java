package com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo;

import com.grocery_store.Skjs.Grocery.Store.Entities.Users.Buser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BUserRepository extends JpaRepository<Buser, Integer> {

    Buser findByEmailOrMoblieNumber(String email, String moblieNumber);

    Buser findById(int id);

    Buser findByEmail(String email);
}
