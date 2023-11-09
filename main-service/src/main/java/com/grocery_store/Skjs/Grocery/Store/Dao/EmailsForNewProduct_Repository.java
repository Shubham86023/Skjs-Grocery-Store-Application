package com.grocery_store.Skjs.Grocery.Store.Dao;

import com.grocery_store.Skjs.Grocery.Store.Entities.EmailsForNewProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailsForNewProduct_Repository extends JpaRepository<EmailsForNewProduct, Integer> {

    EmailsForNewProduct findByEmail(String email);

}
