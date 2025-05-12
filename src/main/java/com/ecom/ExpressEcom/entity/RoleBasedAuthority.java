package com.ecom.ExpressEcom.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
public enum RoleBasedAuthority {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String role;

    RoleBasedAuthority(String role) {
        this.role = role;
    }


}
