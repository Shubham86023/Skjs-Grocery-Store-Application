package com.grocery_store.Skjs.Grocery.Store.Dao.UsersRepo;

import com.grocery_store.Skjs.Grocery.Store.Entities.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailOrMoblieNumber(String email, String moblieNumber);

    User findByEmail(String email);
}
