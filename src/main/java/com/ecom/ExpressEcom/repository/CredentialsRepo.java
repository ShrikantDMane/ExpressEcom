package com.ecom.ExpressEcom.repository;

import com.ecom.ExpressEcom.entity.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.io.Serializable;

@Repository
public interface CredentialsRepo extends JpaRepository<Credentials, Serializable> {
    boolean existsByUserName(String userName);
}
