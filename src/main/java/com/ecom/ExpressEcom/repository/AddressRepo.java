package com.ecom.ExpressEcom.repository;

import com.ecom.ExpressEcom.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;

@Repository
public interface AddressRepo extends JpaRepository<Address, Serializable> {

}
