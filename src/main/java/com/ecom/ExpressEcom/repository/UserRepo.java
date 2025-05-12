package com.ecom.ExpressEcom.repository;

import com.ecom.ExpressEcom.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Serializable> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
