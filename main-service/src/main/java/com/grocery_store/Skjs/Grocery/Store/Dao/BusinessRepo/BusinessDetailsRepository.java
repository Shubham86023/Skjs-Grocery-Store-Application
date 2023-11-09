package com.grocery_store.Skjs.Grocery.Store.Dao.BusinessRepo;

import com.grocery_store.Skjs.Grocery.Store.Entities.Business.BusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessDetailsRepository extends JpaRepository<BusinessDetails, Integer> {

    BusinessDetails findByBuserId(Integer buserId);

}
